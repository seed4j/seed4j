package com.seed4j.module.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.buildProfileActivation;
import static com.seed4j.module.domain.JHipsterModule.buildProfileId;
import static com.seed4j.module.domain.JHipsterModule.buildPropertyKey;
import static com.seed4j.module.domain.JHipsterModule.buildPropertyValue;
import static com.seed4j.module.domain.JHipsterModule.comment;
import static com.seed4j.module.domain.JHipsterModule.dependencyId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.fileStart;
import static com.seed4j.module.domain.JHipsterModule.filesWithExtension;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.JHipsterModule.gradleCorePlugin;
import static com.seed4j.module.domain.JHipsterModule.gradleProfilePlugin;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeRegex;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.mavenBuildExtension;
import static com.seed4j.module.domain.JHipsterModule.mavenPlugin;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.pluginExecution;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.regex;
import static com.seed4j.module.domain.JHipsterModule.scriptCommand;
import static com.seed4j.module.domain.JHipsterModule.scriptKey;
import static com.seed4j.module.domain.JHipsterModule.springProfile;
import static com.seed4j.module.domain.JHipsterModule.text;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.ANGULAR;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;

import com.seed4j.TestFileUtils;
import com.seed4j.module.domain.buildproperties.BuildProperty;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.gradleplugin.GradlePlugin;
import com.seed4j.module.domain.gradleplugin.GradleProfilePlugin;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.MavenBuildExtension;
import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependency.JavaDependencyOptionalValueBuilder;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.javaproperties.SpringProperty;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.module.domain.properties.SpringConfigurationFormat;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JHipsterModulesFixture {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesFixture.class);

  private JHipsterModulesFixture() {}

  public static JHipsterModule moduleSecond(JHipsterModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
    .javaDependencies()
      .addDependency(reflectionsDependency(JavaDependencyScope.COMPILE))
      .addDependencyManagement(commonsLang3DependencyManagement(JavaDependencyScope.IMPORT))
      .and()
     .build();
    // @formatter:on
  }

  public static JHipsterModule module() {
    JHipsterModuleProperties properties = testModuleProperties();
    // @formatter:off
    return moduleBuilder(properties)
    .context()
      .put("packageName", "com.test.myapp")
      .and()
    .files()
      .add(from("init/gitignore"), to(".gitignore"))
      .addExecutable(from("init/.husky/pre-commit"), to(".husky/pre-commit"))
      .batch(from("server/javatool/base/main/error"), to("src/main/java/com/company/myapp/errors"))
        .addTemplate("Assert.java.mustache")
        .addTemplate("AssertionException.java.mustache")
        .and()
      .add(from("server/springboot/core/main/MainApp.java.mustache"), to("src/main/java/com/company/myapp/MyApp.java"))
      .add(from("init/README.md.mustache"), to("README.md"))
      .move(path("dummy.txt"), to("dummy.json"))
      .and()
    .documentation(documentationTitle("Cucumber integration"), from("server/springboot/cucumber/cucumber.md.mustache"))
    .documentation(documentationTitle("Another cucumber integration"), from("server/springboot/cucumber/cucumber.md.mustache"))
    .startupCommands()
      .dockerCompose("src/main/docker/sonar.yml")
      .maven("clean verify sonar:sonar")
      .gradle("clean build sonarqube --info")
      .and()
    .mandatoryReplacements()
      .in(path("src/main/java/com/company/myapp/errors/Assert.java"))
        .add(text("Ensure that the input is not null"), "Dummy replacement")
        .add(regex("will be displayed in an [^ ]+ message"), "Another dummy replacement")
        .add(lineBeforeText("import java.util.Objects;"), "import java.util.Collection;")
        .and()
      .and()
    .optionalReplacements()
      .in(path("src/main/java/com/company/myapp/errors/Assert.java"))
        .add(text("Ensure that the given collection is not empty"), "Dummy collection replacement")
        .add(regex("if the collection is [^ ]+ or empty"), "Another dummy collection replacement")
        .add(lineBeforeRegex("public static final class IntegerAsserter\\s*\\{"), "  // Dummy comment")
        .add(lineBeforeText("import java.time.Instant;"), "import java.math.BigDecimal;")
        .and()
      .in(path("dummy"))
        .add(text("Ensure that the input is not null"), "Dummy replacement")
        .and()
      .and()
    .javaBuildProperties()
      .set(buildPropertyKey("spring-profiles-active"), buildPropertyValue("local"))
      .and()
    .javaDependencies()
      .removeDependency(dependencyId("net.logstash.logback", "logstash-logback-encoder"))
      .addDependency(reflectionsDependency(JavaDependencyScope.TEST))
      .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter"))
      .addDependency(groupId("io.jsonwebtoken"), artifactId("jjwt-api"), versionSlug("json-web-token.version"))
      .addDependency(optionalTestDependency())
      .addDependency(springBootStarterWebDependency())
      .addDependencyManagement(commonsLang3DependencyManagement(JavaDependencyScope.TEST))
      .addDependencyManagement(springBootDependencyManagement())
      .addDependencyManagement(springBootDefaultTypeDependencyManagement())
      .removeDependencyManagement(dependencyId("org.springdoc", "springdoc-openapi-ui"))
      .and()
    .mavenPlugins()
      .pluginManagement(mavenEnforcerPluginManagement())
      .plugin(mavenEnforcerPlugin())
      .plugin(asciidoctorPlugin())
      .and()
    .gradleConfigurations()
      .addConfiguration(
        """
        tasks.build {
          dependsOn("processResources")
        }

        tasks.processResources {
          filesMatching("**/*.yml", "**/*.properties") {
            filter {
              it.replace("@spring.profiles.active@", springProfilesActive)
            }
          }
        }
        """
      )
      .and()
    .gradlePlugins()
      .plugin(jacocoGradlePlugin())
      .plugin(checkstyleGradlePlugin())
      .and()
    .gradleConfigurations()
      .addTasksTestInstruction(
        """
        finalizedBy("jacocoTestReport")\
        """
      )
    .and()
    .javaBuildProfiles()
      .addProfile(localBuildProfile())
        .activation(buildProfileActivation().activeByDefault(false))
        .properties()
          .set(buildPropertyKey("spring.profiles.active"), buildPropertyValue("local"))
          .and()
        .mavenPlugins()
          .pluginManagement(mavenEnforcerPluginManagement())
          .plugin(mavenEnforcerPlugin())
          .and()
        .gradleProfilePlugins()
          .plugin(checkstyleGradleProfilePlugin())
          .plugin(gitPropertiesGradleProfilePlugin())
          .and()
        .javaDependencies()
          .addTestDependency(groupId("org.cassandraunit"), artifactId("cassandra-unit"), versionSlug("cassandraunit"))
          .removeDependency(dependencyId("org.springframework.boot", "spring-boot-starter-web"))
          .removeDependencyManagement(dependencyId("org.springframework.boot", "spring-boot-starter-web"))
          .and()
        .and()
      .and()
    .packageJson()
      .addScript(scriptKey("build"), scriptCommand("ng build --output-path={{projectBuildDirectory}}/classes/static"))
      .addScript(scriptKey("serve"), scriptCommand("tikui-core serve"))
      .addDependency(packageName("@angular/animations"), ANGULAR, packageName("@angular/core"))
      .addDevDependency(packageName("@playwright/test"), COMMON)
      .and()
    .mandatoryReplacements()
      .in(path("package.json"))
        .add(lineBeforeText("  \"engines\":"), jestSonar(properties.indentation()))
      .and()
    .and()
    .preActions()
      .add(() -> log.debug("Applying fixture module"))
      .add(() -> log.debug("You shouldn't add this by default in your modules :D"))
      .and()
    .postActions()
      .add(() -> log.debug("Fixture module applied"))
      .add(context -> log.debug("Applied on {}", context.projectFolder().get()))
      .and()
    .springMainProperties()
      .comment(propertyKey("springdoc.swagger-ui.operationsSorter"), comment("This is a comment"))
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"))
      .and()
    .springLocalProperties()
      .comment(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), comment("This is a comment"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("false"))
      .and()
    .springTestProperties()
      .comment(propertyKey("springdoc.swagger-ui.operationsSorter"), comment("This is a comment"))
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("test"))
      .and()
    .springTestProperties(springProfile("local"))
      .comment(propertyKey("springdoc.swagger-ui"), comment("Swagger properties"))
      .comment(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), comment("This is a comment"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("test"))
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("test"))
      .set(propertyKey("springdoc.swagger-ui.tagsSorter"), propertyValue("test"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("test"))
      .and()
    .springTestFactories()
      .append(propertyKey("o.s.c.ApplicationListener"), propertyValue("c.m.m.MyListener1"))
      .append(propertyKey("o.s.c.ApplicationListener"), propertyValue("c.m.m.MyListener2"))
      .and()
    .gitIgnore()
      .comment("Comment")
      .pattern(".my-hidden-folder/*")
      .and()
    .build();
    // @formatter:on
  }

  private static JavaDependency reflectionsDependency(JavaDependencyScope scope) {
    return javaDependency().groupId("org.reflections").artifactId("reflections").versionSlug("reflections").scope(scope).build();
  }

  private static JavaDependency commonsLang3DependencyManagement(JavaDependencyScope scope) {
    return javaDependency()
      .groupId("org.apache.commons")
      .artifactId("commons-lang3")
      .versionSlug("commons-lang3.version")
      .scope(scope)
      .build();
  }

  private static String jestSonar(Indentation indentation) {
    return """
    %s"jestSonar": {
    %s"reportPath": "{{projectBuildDirectory}}/test-results",
    %s"reportFile": "TESTS-results-sonar.xml"
    %s},
    """.formatted(indentation.spaces(), indentation.times(2), indentation.times(2), indentation.spaces());
  }

  public static JavaDependency springBootStarterWebDependency() {
    return javaDependency()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-web")
      .dependencySlug("spring-boot-starter-web")
      .addExclusion(groupId("org.springframework.boot"), artifactId("spring-boot-starter-tomcat"))
      .build();
  }

  public static JavaDependency springBootDependencyManagement() {
    return javaDependency()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-dependencies")
      .versionSlug("spring-boot.version")
      .scope(JavaDependencyScope.IMPORT)
      .type(JavaDependencyType.POM)
      .build();
  }

  public static JavaDependency springBootDefaultTypeDependencyManagement() {
    return javaDependency()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-dependencies")
      .versionSlug("spring-boot.version")
      .scope(JavaDependencyScope.IMPORT)
      .build();
  }

  public static DependencyId springBootDependencyId() {
    return DependencyId.builder()
      .groupId(groupId("org.springframework.boot"))
      .artifactId(artifactId("spring-boot-dependencies"))
      .type(JavaDependencyType.POM)
      .build();
  }

  public static JavaDependency defaultVersionDependency() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
  }

  public static JavaDependency dependencyWithVersion() {
    return javaDependency().groupId("io.jsonwebtoken").artifactId("jjwt-jackson").versionSlug("json-web-token").build();
  }

  public static JavaDependency dependencyWithVersionAndExclusion() {
    return javaDependency()
      .groupId("io.jsonwebtoken")
      .artifactId("jjwt-jackson")
      .versionSlug("json-web-token")
      .addExclusion(DependencyId.of(new GroupId("com.fasterxml.jackson.core"), new ArtifactId("jackson-databind")))
      .build();
  }

  public static JavaBuildCommands javaDependenciesCommands() {
    SetVersion setVersion = new SetVersion(springBootVersion());
    RemoveDirectJavaDependency remove = new RemoveDirectJavaDependency(
      DependencyId.of(new GroupId("spring-boot"), new ArtifactId("1.2.3"))
    );
    AddDirectJavaDependency add = new AddDirectJavaDependency(optionalTestDependency());

    return new JavaBuildCommands(List.of(setVersion, remove, add));
  }

  public static JavaDependency optionalTestDependency() {
    return optionalTestDependencyBuilder().build();
  }

  public static MavenBuildExtension mavenBuildExtensionWithSlug() {
    return mavenBuildExtension().groupId("kr.motd.maven").artifactId("os-maven-plugin").versionSlug("os-maven-plugin.version").build();
  }

  public static JavaDependencyOptionalValueBuilder optionalTestDependencyBuilder() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-engine")
      .versionSlug("spring-boot")
      .classifier("test")
      .scope(JavaDependencyScope.TEST)
      .optional();
  }

  public static DependencyId jsonWebTokenDependencyId() {
    return DependencyId.of(new GroupId("io.jsonwebtoken"), new ArtifactId("jjwt-api"));
  }

  public static BuildProfileId localBuildProfile() {
    return buildProfileId("local");
  }

  public static BuildProperty springProfilesActiveProperty() {
    return new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("local"));
  }

  public static SeedModuleContext context() {
    return SeedModuleContext.builder(emptyModuleBuilder()).put("packageName", "com.test.myapp").build();
  }

  public static JHipsterModuleBuilder emptyModuleBuilder() {
    return moduleBuilder(testModuleProperties());
  }

  public static SeedModuleContext emptyModuleContext() {
    return SeedModuleContext.builder(emptyModuleBuilder()).build();
  }

  public static JHipsterModuleProperties testModuleProperties() {
    return new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), true, null);
  }

  public static JavaDependenciesVersions currentJavaDependenciesVersion() {
    return new JavaDependenciesVersions(List.of(springBootVersion(), problemVersion(), mavenEnforcerVersion(), jsonWebTokenVersion()));
  }

  public static JavaDependencyVersion jsonWebTokenVersion() {
    return new JavaDependencyVersion("json-web-token", "1.2.3");
  }

  private static JavaDependencyVersion problemVersion() {
    return new JavaDependencyVersion("problem-spring", "3.4.5");
  }

  public static JavaDependencyVersion springBootVersion() {
    return new JavaDependencyVersion("spring-boot", "1.2.3");
  }

  public static JavaDependencyVersion mavenEnforcerVersion() {
    return new JavaDependencyVersion("maven-enforcer-plugin", "1.1.1");
  }

  public static JHipsterModuleProperties allProperties() {
    return new JHipsterModuleProperties(
      "/test",
      true,
      Map.of(
        "packageName",
        "com.seed4j.growth",
        "indentSize",
        2,
        "projectName",
        "JHipster project",
        "baseName",
        "jhipster",
        "optionalString",
        "optional",
        "mandatoryInteger",
        42,
        "mandatoryBoolean",
        true,
        "optionalBoolean",
        true
      )
    );
  }

  public static JHipsterModulePropertiesBuilder propertiesBuilder(String projectFolder) {
    return new JHipsterModulePropertiesBuilder(projectFolder);
  }

  public static SpringProperty springLocalMainProperty() {
    return SpringProperty.builder(SpringPropertyType.MAIN_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .profile(springProfile("local"))
      .build();
  }

  public static SpringProperty springMainProperty() {
    return SpringProperty.builder(SpringPropertyType.MAIN_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static SpringProperty springTestProperty() {
    return SpringProperty.builder(SpringPropertyType.TEST_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static MavenPlugin mavenEnforcerPluginManagement() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-enforcer-plugin")
      .versionSlug("maven-enforcer-plugin")
      .addDependency(dependencyWithVersionAndExclusion())
      .addDependency(groupId("io.jsonwebtoken"), artifactId("jjwt-jackson"), versionSlug("json-web-token"))
      .addExecution(pluginExecution().goals("enforce").id("enforce-versions"))
      .addExecution(
        pluginExecution()
          .goals("enforce")
          .id("enforce-dependencyConvergence")
          .configuration(
            """
            <rules>
              <DependencyConvergence/>
            </rules>
            <fail>false</fail>
            """
          )
      )
      .configuration(
        """
        <rules>
          <requireMavenVersion>
            <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
            <version>[${maven.version},)</version>
          </requireMavenVersion>
          <requireJavaVersion>
              <message>You are running an incompatible version of Java. JHipster engine supports JDK 21+.</message>
              <version>[21,22)</version>
          </requireJavaVersion>
        </rules>
        """
      )
      .build();
  }

  public static GradleMainBuildPlugin jacocoGradlePlugin() {
    return gradleCorePlugin()
      .id("jacoco")
      .toolVersionSlug("jacoco")
      .configuration(
        """
        jacoco {
          toolVersion = libs.versions.jacoco.get()
        }

        tasks.jacocoTestReport {
          dependsOn("test", "integrationTest")
          reports {
            xml.required.set(true)
            html.required.set(false)
          }
          executionData.setFrom(fileTree(buildDir).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
        }
        """
      )
      .build();
  }

  public static GradleMainBuildPlugin checkstyleGradlePlugin() {
    return gradleCorePlugin()
      .id("checkstyle")
      .toolVersionSlug("checkstyle")
      .withBuildGradleImport("java.util.Properties")
      .configuration(
        """
        checkstyle {
          toolVersion = libs.versions.checkstyle.get()
        }
        """
      )
      .build();
  }

  public static GradleProfilePlugin checkstyleGradleProfilePlugin() {
    return gradleCorePlugin()
      .id("checkstyle")
      .toolVersionSlug("checkstyle")
      .withBuildGradleImport("java.util.Properties")
      .configuration(
        """
        checkstyle {
          toolVersion = libs.versions.checkstyle.get()
        }
        """
      )
      .build();
  }

  public static GradlePlugin nodeGradlePlugin() {
    return gradleCommunityPlugin()
      .id("com.github.node-gradle.node")
      .pluginSlug("node-gradle")
      .versionSlug("node-gradle")
      .withBuildGradleImport("com.github.gradle.node.npm.task.NpmTask")
      .configuration(
        """
        node {
          version.set("v20.14.0")
          npmVersion.set("10.8.1")
          npmWorkDir.set(file("build"))
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
          npmCommand.set(listOf("run", "test"))
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

  public static GradleProfilePlugin gitPropertiesGradleProfilePlugin() {
    return gradleProfilePlugin()
      .id("com.gorylenko.gradle-git-properties")
      .dependency(groupId("com.gorylenko.gradle-git-properties"), artifactId("gradle-git-properties"))
      .versionSlug("git-properties")
      .configuration(
        """
        gitProperties {
          failOnNoGitDirectory = false
          keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
        }
        """
      )
      .build();
  }

  public static GradleProfilePlugin dockerGradlePluginDependency() {
    return gradleProfilePlugin()
      .id("com.bmuschko.docker-remote-api")
      .dependency(groupId("com.bmuschko"), artifactId("gradle-docker-plugin"))
      .versionSlug("docker-plugin")
      .withBuildGradleImport("java.util.Properties")
      .build();
  }

  public static JavaDependencyVersion checkstyleToolVersion() {
    return new JavaDependencyVersion("checkstyle", "8.42.1");
  }

  public static MavenPlugin mavenEnforcerPlugin() {
    return mavenPlugin().groupId("org.apache.maven.plugins").artifactId("maven-enforcer-plugin").build();
  }

  public static MavenPlugin asciidoctorPlugin() {
    GroupId asciidoctorGroupId = new GroupId("org.asciidoctor");
    return mavenPlugin()
      .groupId(asciidoctorGroupId)
      .artifactId("asciidoctor-maven-plugin")
      .addDependency(javaDependency().groupId(asciidoctorGroupId).artifactId("asciidoctorj-screenshot").build())
      .addDependency(javaDependency().groupId(asciidoctorGroupId).artifactId("asciidoctorj-diagram").build())
      .build();
  }

  public static JHipsterModulesToApply modulesToApply() {
    return new JHipsterModulesToApply(
      List.of(moduleSlug("maven-java"), moduleSlug("init")),
      propertiesBuilder("/dummy")
        .projectName("Growth Project")
        .basePackage("com.seed4j.growth")
        .put("baseName", "growth")
        .put("serverPort", 8080)
        .build()
    );
  }

  public static SeedModuleSlug moduleSlug(String slug) {
    return new SeedModuleSlug(slug);
  }

  public static SeedFeatureSlug featureSlug(String slug) {
    return new SeedFeatureSlug(slug);
  }

  public static JHipsterModuleUpgrade upgrade() {
    return JHipsterModuleUpgrade.builder()
      .doNotAdd(to(".husky/pre-commit"))
      .delete(path("documentation/cucumber-integration.md"))
      .replace(filesWithExtension("java"), fileStart(), "// This is an updated file\n\n")
      .build();
  }

  public static final class JHipsterModulePropertiesBuilder {

    private boolean commitModules = false;
    private final String projectFolder;
    private final Map<String, Object> properties = new HashMap<>();

    private JHipsterModulePropertiesBuilder(String projectFolder) {
      this.projectFolder = projectFolder;
    }

    public JHipsterModulePropertiesBuilder commitModules() {
      commitModules = true;

      return this;
    }

    public JHipsterModulePropertiesBuilder basePackage(String basePackage) {
      properties.put("packageName", basePackage);

      return this;
    }

    public JHipsterModulePropertiesBuilder projectBaseName(String projectBaseName) {
      properties.put("baseName", projectBaseName);

      return this;
    }

    public JHipsterModulePropertiesBuilder projectName(String projectName) {
      properties.put("projectName", projectName);

      return this;
    }

    public JHipsterModulePropertiesBuilder nodePackageManager(NodePackageManager nodePackageManager) {
      Assert.notNull("nodePackageManager", nodePackageManager);
      properties.put("nodePackageManager", nodePackageManager.propertyKey());

      return this;
    }

    public JHipsterModulePropertiesBuilder springConfigurationFormat(SpringConfigurationFormat format) {
      Assert.notNull("format", format);
      properties.put("springConfigurationFormat", format.get());

      return this;
    }

    public JHipsterModulePropertiesBuilder put(String key, Object value) {
      properties.put(key, value);

      return this;
    }

    public JHipsterModuleProperties build() {
      return new JHipsterModuleProperties(projectFolder, commitModules, properties);
    }
  }
}
