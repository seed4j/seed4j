package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.mavenplugin.MavenBuildPhase.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.gradleplugin.GradleMainBuildPlugin;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class FrontendJavaBuildToolModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/frontend");

  private static final String REDIRECTION = "wire/frontend";
  private static final String REDIRECTION_PRIMARY = REDIRECTION + "/infrastructure/primary";

  private final NpmVersions npmVersions;

  public FrontendJavaBuildToolModuleFactory(NpmVersions npmVersions) {
    this.npmVersions = npmVersions;
  }

  public JHipsterModule buildFrontendMavenModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return commonModuleFiles(properties)
      .javaBuildProperties()
        .set(buildPropertyKey("node.version"), buildPropertyValue("v" + npmVersions.nodeVersion().get()))
        .set(buildPropertyKey("npm.version"), buildPropertyValue(npmVersions.get("npm", JHLiteNpmVersionSource.COMMON).get()))
        .and()
      .mavenPlugins()
        .plugin(checksumPlugin())
        .plugin(antrunPlugin())
        .plugin(frontendMavenPlugin())
        .and()
      .build();
    //@formatter:on
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

  private MavenPlugin antrunPlugin() {
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
              <condition property="skip.npm" value="true" else="false">
                <and>
                  <available file="checksums.csv" filepath="${project.build.directory}" />
                  <available file="checksums.csv.old" filepath="${project.build.directory}" />
                  <filesmatch file1="${project.build.directory}/checksums.csv" file2="${project.build.directory}/checksums.csv.old" />
                </and>
              </condition>
            </target>
            <exportAntProperties>true</exportAntProperties>
            """
          )
      )
      .build();
  }

  private MavenPlugin frontendMavenPlugin() {
    return mavenPlugin()
      .groupId("com.github.eirslett")
      .artifactId("frontend-maven-plugin")
      .versionSlug("frontend-maven-plugin")
      .configuration("<installDirectory>${project.build.directory}</installDirectory>")
      .addExecution(
        pluginExecution()
          .goals("install-node-and-npm")
          .id("install-node-and-npm")
          .configuration(
            """
            <nodeVersion>${node.version}</nodeVersion>
            <npmVersion>${npm.version}</npmVersion>
            """
          )
      )
      .addExecution(pluginExecution().goals("npm").id("npm install"))
      .addExecution(
        pluginExecution()
          .goals("npm")
          .id("build front")
          .phase(GENERATE_RESOURCES)
          .configuration(
            """
            <arguments>run build</arguments>
            <environmentVariables>
              <APP_VERSION>${project.version}</APP_VERSION>
            </environmentVariables>
            <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
            """
          )
      )
      .addExecution(
        pluginExecution()
          .goals("npm")
          .id("front test")
          .phase(TEST)
          .configuration(
            """
            <arguments>run test:coverage</arguments>
            <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
            """
          )
      )
      .build();
  }

  public JHipsterModule buildFrontendGradleModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return commonModuleFiles(properties)
      .javaBuildProperties()
        .set(buildPropertyKey("node.version.value"), buildPropertyValue(npmVersions.nodeVersion().get()))
        .set(buildPropertyKey("npm.version.value"), buildPropertyValue(npmVersions.get("npm", JHLiteNpmVersionSource.COMMON).get()))
        .and()
      .gradlePlugins()
        .plugin(frontendGradlePlugin())
        .and()
      .gradleConfigurations()
        .addTasksTestInstruction(
          """
          dependsOn("testNpm")\
          """
        )
        .and()
      .build();
    //@formatter:on
  }

  private static JHipsterModuleBuilder commonModuleFiles(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath);
    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          SOURCE.template("RedirectionResource.java"),
          mainDestination.append(REDIRECTION_PRIMARY).append("RedirectionResource.java")
        )
        .add(SOURCE.template(PACKAGE_INFO), mainDestination.append(REDIRECTION).append(PACKAGE_INFO))
        .and();
    //@formatter:on
  }

  private GradleMainBuildPlugin frontendGradlePlugin() {
    return gradleCommunityPlugin()
      .id("com.github.node-gradle.node")
      .pluginSlug("node-gradle")
      .versionSlug("node-gradle")
      .withBuildGradleImport("com.github.gradle.node.npm.task.NpmTask")
      .configuration(
        """
        node {
          download.set(true)
          version.set(nodeVersionValue)
          npmVersion.set(npmVersionValue)
          workDir.set(file(layout.buildDirectory))
          npmWorkDir.set(file(layout.buildDirectory))
        }

        val buildTaskUsingNpm = tasks.register<NpmTask>("buildNpm") {
          description = "Build the frontend project using NPM"
          group = "Build"
          dependsOn("npmInstall")
          npmCommand.set(listOf("run", "build"))
          environment.set(mapOf("APP_VERSION" to project.version.toString()))
        }

        val testTaskUsingNpm = tasks.register<NpmTask>("testNpm") {
          description = "Test the frontend project using NPM"
          group = "verification"
          dependsOn("npmInstall", "buildNpm")
          npmCommand.set(listOf("run", "test:coverage"))
          ignoreExitValue.set(false)
          workingDir.set(projectDir)
          execOverrides {
            standardOutput = System.out
          }
        }

        tasks.bootJar {
          dependsOn("buildNpm")
          from("build/classes/static") {
              into("BOOT-INF/classes/static")
          }
        }
        """
      )
      .build();
  }
}
