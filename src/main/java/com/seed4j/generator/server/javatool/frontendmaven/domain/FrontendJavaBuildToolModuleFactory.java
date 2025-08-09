package com.seed4j.generator.server.javatool.frontendmaven.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.buildPropertyKey;
import static com.seed4j.module.domain.JHipsterModule.buildPropertyValue;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.JHipsterModule.mavenPlugin;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.pluginExecution;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.COMPILE;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.GENERATE_RESOURCES;
import static com.seed4j.module.domain.nodejs.NodePackageManager.NPM;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class FrontendJavaBuildToolModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";

  private static final String PROPERTIES_FIELD = "properties";

  private static final SeedSource SOURCE = from("server/springboot/mvc/frontend");

  private static final String REDIRECTION = "wire/frontend";
  private static final String REDIRECTION_PRIMARY = REDIRECTION + "/infrastructure/primary";

  private final NodeVersions nodeVersions;

  public FrontendJavaBuildToolModuleFactory(NodeVersions nodeVersions) {
    this.nodeVersions = nodeVersions;
  }

  public JHipsterModule buildFrontendMavenModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    JHipsterModuleBuilder moduleBuilder = commonModuleFiles(properties);

    NodePackageManager nodePackageManager = properties.nodePackageManager();
    if (nodePackageManager == NPM) {
      moduleBuilder = moduleBuilder
        .javaBuildProperties()
        .set(buildPropertyKey("npm.version"), buildPropertyValue(nodeVersions.packageManagerVersion(nodePackageManager).get()))
        .and();
    }

    // @formatter:off
    return moduleBuilder
      .javaBuildProperties()
        .set(buildPropertyKey("node.version"), buildPropertyValue("v" + nodeVersions.nodeVersion().get()))
        .and()
      .mavenPlugins()
        .plugin(frontendMavenPlugin(nodePackageManager).build())
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildFrontendMavenCacheModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(checksumPlugin())
        .plugin(antrunPlugin(properties.nodePackageManager()))
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin checksumPlugin() {
    return mavenPlugin()
      .groupId("net.nicoulaj.maven.plugins")
      .artifactId("checksum-maven-plugin")
      .versionSlug("checksum-maven-plugin")
      .addExecution(pluginExecution().goals("files").id("create-pre-compiled-webapp-checksum").phase(GENERATE_RESOURCES))
      .addExecution(
        pluginExecution()
          .goals("files")
          .id("create-compiled-webapp-checksum")
          .phase(COMPILE)
          .configuration(
            """
            <csvSummaryFile>checksums.csv.old</csvSummaryFile>
            """
          )
      )
      .configuration(
        """
          <fileSets>
            <fileSet>
              <directory>${project.basedir}</directory>
              <includes>
                <include>src/main/webapp/**/*.*</include>
                <include>target/classes/static/**/*.*</include>
                <include>package-lock.json</include>
                <include>package.json</include>
                <include>tsconfig.json</include>
              </includes>
            </fileSet>
          </fileSets>
          <failOnError>false</failOnError>
          <failIfNoFiles>false</failIfNoFiles>
          <individualFiles>false</individualFiles>
          <algorithms>
            <algorithm>SHA-1</algorithm>
          </algorithms>
          <includeRelativePath>true</includeRelativePath>
          <quiet>true</quiet>
        """
      )
      .build();
  }

  private MavenPlugin antrunPlugin(NodePackageManager nodePackageManager) {
    String skipProperty = switch (nodePackageManager) {
      case NPM -> "skip.npm";
      case PNPM -> "skip.corepack";
    };
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-antrun-plugin")
      .versionSlug("maven-antrun-plugin")
      .addExecution(
        pluginExecution()
          .goals("run")
          .id("eval-frontend-checksum")
          .phase(GENERATE_RESOURCES)
          .configuration(
            """
            <target>
              <condition property="%s" value="true" else="false">
                <and>
                  <available file="checksums.csv" filepath="${project.build.directory}" />
                  <available file="checksums.csv.old" filepath="${project.build.directory}" />
                  <filesmatch file1="${project.build.directory}/checksums.csv" file2="${project.build.directory}/checksums.csv.old" />
                </and>
              </condition>
            </target>
            <exportAntProperties>true</exportAntProperties>
            """.formatted(skipProperty)
          )
      )
      .build();
  }

  private MavenPlugin.MavenPluginOptionalBuilder frontendMavenPlugin(NodePackageManager nodePackageManager) {
    return mavenPlugin()
      .groupId("com.github.eirslett")
      .artifactId("frontend-maven-plugin")
      .versionSlug("frontend-maven-plugin")
      .configuration("<installDirectory>${project.build.directory}</installDirectory>")
      .addExecution(MavenFrontendPluginExecutions.installNode(nodePackageManager))
      .addExecution(MavenFrontendPluginExecutions.installPackages(nodePackageManager))
      .addExecution(MavenFrontendPluginExecutions.buildFront(nodePackageManager))
      .addExecution(MavenFrontendPluginExecutions.testFront(nodePackageManager));
  }

  public JHipsterModule buildFrontendGradleModule(SeedModuleProperties properties) {
    NodePackageManager nodePackageManager = properties.nodePackageManager();
    // @formatter:off
    return commonModuleFiles(properties)
      .javaBuildProperties()
        .set(buildPropertyKey("node.version.value"), buildPropertyValue(nodeVersions.nodeVersion().get()))
        .set(buildPropertyKey("%s.version.value".formatted(nodePackageManager)), buildPropertyValue(nodeVersions.packageManagerVersion(nodePackageManager).get()))
        .and()
      .gradlePlugins()
        .plugin(frontendGradlePlugin(nodePackageManager))
        .and()
      .gradleConfigurations()
        .addTasksTestInstruction(
          """
          dependsOn("test%s")\
          """.formatted(capitalize(nodePackageManager.command()))
        )
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildMergeCypressCoverageModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);
    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(mergeCypressPlugin(properties.nodePackageManager()))
      .and()
      .build();
    // @formatter:on
  }

  private static JHipsterModuleBuilder commonModuleFiles(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    String packagePath = properties.packagePath();

    SeedDestination mainDestination = toSrcMainJava().append(packagePath);
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          SOURCE.template("RedirectionResource.java"),
          mainDestination.append(REDIRECTION_PRIMARY).append("RedirectionResource.java")
        )
        .add(SOURCE.template(PACKAGE_INFO), mainDestination.append(REDIRECTION).append(PACKAGE_INFO))
        .and();
    // @formatter:on
  }

  private GradleMainBuildPlugin frontendGradlePlugin(NodePackageManager nodePackageManager) {
    String taskImport = switch (nodePackageManager) {
      case NPM -> "com.github.gradle.node.npm.task.NpmTask";
      case PNPM -> "com.github.gradle.node.pnpm.task.PnpmTask";
    };
    return gradleCommunityPlugin()
      .id("com.github.node-gradle.node")
      .pluginSlug("node-gradle")
      .versionSlug("node-gradle")
      .withBuildGradleImport(taskImport)
      .configuration(
        """
        node {
          download.set(true)
          version.set(nodeVersionValue)
          {{nodePackageManager}}Version.set({{nodePackageManager}}VersionValue)
          workDir.set(file(layout.buildDirectory))
          {{nodePackageManager}}WorkDir.set(file(layout.buildDirectory))
        }

        val buildTaskUsing{{nodePackageManagerCapitalized}} = tasks.register<{{nodePackageManagerCapitalized}}Task>("build{{nodePackageManagerCapitalized}}") {
          description = "Build the frontend project using {{nodePackageManager}}"
          group = "Build"
          dependsOn("{{nodePackageManager}}Install")
          {{nodePackageManager}}Command.set(listOf("run", "build"))
          environment.set(mapOf("APP_VERSION" to project.version.toString()))
        }

        val testTaskUsing{{nodePackageManagerCapitalized}} = tasks.register<{{nodePackageManagerCapitalized}}Task>("test{{nodePackageManagerCapitalized}}") {
          description = "Test the frontend project using {{nodePackageManager}}"
          group = "verification"
          dependsOn("{{nodePackageManager}}Install", "build{{nodePackageManagerCapitalized}}")
          {{nodePackageManager}}Command.set(listOf("run", "test:coverage"))
          ignoreExitValue.set(false)
          workingDir.set(projectDir)
          execOverrides {
            standardOutput = System.out
          }
        }

        tasks.bootJar {
          dependsOn("build{{nodePackageManagerCapitalized}}")
          from("build/classes/static") {
              into("BOOT-INF/classes/static")
          }
        }
        """.replace("{{nodePackageManager}}", nodePackageManager.command())
          .replace("{{nodePackageManagerCapitalized}}", capitalize(nodePackageManager.command()))
      )
      .build();
  }

  private MavenPlugin mergeCypressPlugin(NodePackageManager nodePackageManager) {
    return frontendMavenPlugin(nodePackageManager)
      .addExecution(MavenFrontendPluginExecutions.componentTest(nodePackageManager))
      .addExecution(MavenFrontendPluginExecutions.testCoverageCheck(nodePackageManager))
      .build();
  }
}
