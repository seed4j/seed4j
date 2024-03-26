package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.module.domain.buildproperties.BuildProperty;
import tech.jhipster.lite.module.domain.buildproperties.PropertyKey;
import tech.jhipster.lite.module.domain.buildproperties.PropertyValue;
import tech.jhipster.lite.module.domain.gradleplugin.GradlePlugin;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.MavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileId;
import tech.jhipster.lite.module.domain.javadependency.*;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyOptionalValueBuilder;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public final class JHipsterModulesFixture {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesFixture.class);

  private JHipsterModulesFixture() {}

  public static JHipsterModule fullModule() {
    // @formatter:off
   return moduleBuilder(testModuleProperties())
    .context()
      .put("packageName", "com.test.myapp")
      .and()
    .files()
      .add(from("init/gitignore"), to(".gitignore"))
      .addExecutable(from("prettier/.husky/pre-commit"), to(".husky/pre-commit"))
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
        .add(regex("will be displayed in [^ ]+ message"), "Another dummy replacement")
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
      .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter"))
      .addDependency(groupId("io.jsonwebtoken"), artifactId("jjwt-api"), versionSlug("json-web-token.version"))
      .addDependency(optionalTestDependency())
      .addDependency(springBootStarterWebDependency())
      .addDependencyManagement(springBootDependencyManagement())
      .addDependencyManagement(springBootDefaultTypeDependencyManagement())
      .removeDependencyManagement(dependencyId("org.springdoc", "springdoc-openapi-ui"))
      .and()
    .mavenPlugins()
      .pluginManagement(mavenEnforcerPluginManagement())
      .plugin(mavenEnforcerPlugin())
      .plugin(asciidoctorPlugin())
      .and()
    .gradlePlugins()
      .plugin(jacocoGradlePlugin())
      .plugin(checkstyleGradlePlugin())
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
        .javaDependencies()
          .addTestDependency(groupId("org.cassandraunit"), artifactId("cassandra-unit"), versionSlug("cassandraunit"))
          .removeDependency(dependencyId("org.springframework.boot", "spring-boot-starter-web"))
          .removeDependencyManagement(dependencyId("org.springframework.boot", "spring-boot-starter-web"))
          .and()
        .and()
      .and()
    .packageJson()
      .addScript(scriptKey("serve"), scriptCommand("tikui-core serve"))
      .addDependency(packageName("@angular/animations"), VersionSource.ANGULAR, packageName("@angular/core"))
      .addDevDependency(packageName("@playwright/test"), VersionSource.COMMON)
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
    .build();
    // @formatter:on
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

  public static JHipsterModuleContext context() {
    return JHipsterModuleContext.builder(emptyModuleBuilder()).put("packageName", "com.test.myapp").build();
  }

  public static JHipsterModuleBuilder emptyModuleBuilder() {
    return moduleBuilder(testModuleProperties());
  }

  public static JHipsterModuleProperties testModuleProperties() {
    return new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), true, null, List.of(), List.of());
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
        "tech.jhipster.chips",
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
      ),
      List.of(),
      List.of()
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

  public static GradlePlugin jacocoGradlePlugin() {
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

  public static GradlePlugin checkstyleGradlePlugin() {
    return gradleCorePlugin()
      .id("checkstyle")
      .toolVersionSlug("checkstyle")
      .configuration(
        """
        checkstyle {
          toolVersion = libs.versions.checkstyle.get()
        }
        """
      )
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
    var maven = moduleSlug("maven-java");
    var init = moduleSlug("init");
    return new JHipsterModulesToApply(
      List.of(maven, init),
      propertiesBuilder("/dummy")
        .projectName("Chips Project")
        .basePackage("tech.jhipster.chips")
        .put("baseName", "chips")
        .put("serverPort", 8080)
        .module(maven)
        .module(init)
        .build()
    );
  }

  public static JHipsterModuleSlug moduleSlug(String slug) {
    return new JHipsterModuleSlug(slug);
  }

  public static JHipsterFeatureSlug featureSlug(String slug) {
    return new JHipsterFeatureSlug(slug);
  }

  public static JHipsterModuleUpgrade upgrade() {
    return JHipsterModuleUpgrade.builder()
      .doNotAdd(to(".gitignore"))
      .delete(path("documentation/cucumber-integration.md"))
      .replace(filesWithExtension("java"), fileStart(), "// This is an updated file\n\n")
      .build();
  }

  public static final class JHipsterModulePropertiesBuilder {

    private boolean commitModules = false;
    private final String projectFolder;
    private final Map<String, Object> properties = new HashMap<>();
    private final Set<JHipsterModuleSlug> newModules = new HashSet<>();
    private final Set<JHipsterModuleSlug> alreadyApplied = new HashSet<>();

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

    public JHipsterModulePropertiesBuilder module(JHipsterModuleSlug module) {
      newModules.add(module);

      return this;
    }

    public JHipsterModulePropertiesBuilder alreadyApplied(JHipsterModuleSlug module) {
      alreadyApplied.add(module);

      return this;
    }

    public JHipsterModulePropertiesBuilder put(String key, Object value) {
      properties.put(key, value);

      return this;
    }

    public JHipsterModuleProperties build() {
      return new JHipsterModuleProperties(projectFolder, commitModules, properties, newModules, alreadyApplied);
    }
  }
}
