package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyOptionalValueBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public final class JHipsterModulesFixture {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesFixture.class);

  private JHipsterModulesFixture() {}

  public static JHipsterModule module() {
    // @formatter:off
   return moduleBuilder(testModuleProperties())
    .context()
      .put("packageName", "com.test.myapp")
      .and()
    .files()
      .add(from("init/gitignore"), to(".gitignore"))
      .addExecutable(from("prettier/.husky/pre-commit"), to(".husky/pre-commit"))
      .batch(from("server/javatool/base"), to("src/main/java/com/company/myapp/errors"))
        .addTemplate("Assert.java.mustache")
        .addTemplate("AssertionException.java.mustache")
        .and()
        .add(from("server/springboot/core/MainApp.java.mustache"), to("src/main/java/com/company/myapp/MyApp.java"))
      .add(from("init/README.md.mustache"), to("README.md"))
      .move(path("dummy.txt"), to("dummy.json"))
      .and()
    .documentation(documentationTitle("Cucumber integration"), from("server/springboot/cucumber/cucumber.md.mustache"))
    .documentation(documentationTitle("Another cucumber integration"), from("server/springboot/cucumber/cucumber.md.mustache"))
    .startupCommand("This is a startup section")
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
        .add(lineBeforeRegex("public static class IntegerAsserter\\s*\\{"), "  // Dummy comment")
        .add(lineBeforeText("import java.time.Instant;"), "import java.math.BigDecimal;")
        .and()
      .in(path("dummy"))
        .add(text("Ensure that the input is not null"), "Dummy replacement")
        .and()
      .and()
    .javaDependencies()
      .setVersion(javaDependencyVersion("dummy-dependency", "4.5.8"))
      .removeDependency(dependencyId("net.logstash.logback", "logstash-logback-encoder"))
      .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter"))
      .addDependency(groupId("io.jsonwebtoken"), artifactId("jjwt-api"), versionSlug("jjwt.version"))
      .addDependency(optionalTestDependency())
      .addDependency(springBootStarterWebDependency())
      .addDependencyManagement(springBootDependencyManagement())
      .addDependencyManagement(springBootDefaultTypeDependencyManagement())
      .and()
    .javaBuildPlugins()
      .pluginManagement(mavenEnforcerPluginManagement())
      .plugin(mavenEnforcerPlugin())
      .and()
    .packageJson()
      .addScript(scriptKey("serve"), scriptCommand("tikui-core serve"))
      .addDependency(packageName("@angular/animations"), VersionSource.ANGULAR)
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
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"))
      .and()
    .springMainProperties(springProfile("local"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("false"))
      .and()
    .springTestProperties()
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("test"))
      .and()
    .springTestProperties(springProfile("local"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("test"))
      .and()
    .build();
    // @formatter:on
  }

  public static JavaDependency springBootStarterWebDependency() {
    return javaDependency()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-web")
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
    return new DependencyId(groupId("org.springframework.boot"), artifactId("spring-boot-dependencies"));
  }

  public static JavaDependency defaultVersionDependency() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
  }

  public static JavaBuildCommands javaDependenciesCommands() {
    SetVersion setVersion = new SetVersion(springBootVersion());
    RemoveDirectJavaDependency remove = new RemoveDirectJavaDependency(
      new DependencyId(new GroupId("spring-boot"), new ArtifactId("1.2.3"))
    );
    AddDirectJavaDependency add = new AddDirectJavaDependency(optionalTestDependency());

    return new JavaBuildCommands(List.of(setVersion, remove, add));
  }

  public static JavaDependency optionalTestDependency() {
    return optionalTestDependencyBuilder().build();
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
    return new DependencyId(new GroupId("io.jsonwebtoken"), new ArtifactId("jjwt-api"));
  }

  public static JHipsterModuleContext context() {
    return JHipsterModuleContext.builder(emptyModuleBuilder()).put("packageName", "com.test.myapp").build();
  }

  public static JHipsterModuleBuilder emptyModuleBuilder() {
    return moduleBuilder(testModuleProperties());
  }

  public static JHipsterModuleProperties testModuleProperties() {
    return new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), true, null);
  }

  public static JavaDependenciesVersions currentJavaDependenciesVersion() {
    return new JavaDependenciesVersions(List.of(springBootVersion(), problemVersion(), mavenEnforcerVersion()));
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
      )
    );
  }

  public static JHipsterModulePropertiesBuilder propertiesBuilder(String projectFolder) {
    return new JHipsterModulePropertiesBuilder(projectFolder);
  }

  public static SpringProperty springLocalMainProperty() {
    return SpringProperty
      .builder(SpringPropertyType.MAIN_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .profile(springProfile("local"))
      .build();
  }

  public static SpringProperty springMainProperty() {
    return SpringProperty
      .builder(SpringPropertyType.MAIN_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static SpringProperty springTestProperty() {
    return SpringProperty
      .builder(SpringPropertyType.TEST_PROPERTIES)
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static JavaBuildPlugin mavenEnforcerPluginManagement() {
    return javaBuildPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-enforcer-plugin")
      .versionSlug("maven-enforcer-plugin")
      .additionalElements(
        """
        <executions><execution><id>enforce-versions</id>
            <goals>
              <goal>enforce</goal>
            </goals>
          </execution>
          <execution>
            <id>enforce-dependencyConvergence</id>
            <configuration>
              <rules>
                <DependencyConvergence/>
              </rules>
              <fail>false</fail>
            </configuration>
            <goals>
              <goal>enforce</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <rules>
            <requireMavenVersion>
              <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
              <version>[${maven.version},)</version>
            </requireMavenVersion>
            <requireJavaVersion>
              <message>You are running an incompatible version of Java. JHipster supports JDK 17.</message>
              <version>[17,18)</version>
            </requireJavaVersion>
          </rules>
        </configuration>
        """
      )
      .build();
  }

  public static JavaBuildPlugin mavenEnforcerPlugin() {
    return javaBuildPlugin().groupId("org.apache.maven.plugins").artifactId("maven-enforcer-plugin").build();
  }

  public static JHipsterModulesToApply modulesToApply() {
    return new JHipsterModulesToApply(
      List.of(moduleSlug("maven-java"), moduleSlug("init")),
      propertiesBuilder("/dummy")
        .projectName("Chips Project")
        .basePackage("tech.jhipster.chips")
        .put("baseName", "chips")
        .put("serverPort", 8080)
        .build()
    );
  }

  public static JHipsterModuleSlug moduleSlug(String slug) {
    return new JHipsterModuleSlug(slug);
  }

  public static JHipsterFeatureSlug featureSlug(String slug) {
    return new JHipsterFeatureSlug(slug);
  }

  public static class JHipsterModulePropertiesBuilder {

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

    public JHipsterModulePropertiesBuilder put(String key, Object value) {
      properties.put(key, value);

      return this;
    }

    public JHipsterModuleProperties build() {
      return new JHipsterModuleProperties(projectFolder, commitModules, properties);
    }
  }
}
