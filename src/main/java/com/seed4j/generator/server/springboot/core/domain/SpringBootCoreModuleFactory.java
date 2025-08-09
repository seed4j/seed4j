package com.seed4j.generator.server.springboot.core.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.dependencyId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.mavenPlugin;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.pluginExecution;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notContaining;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootCoreModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/core");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId SPRING_BOOT_GROUP = groupId("org.springframework.boot");

  private static final String JUNIT_GROUP = "org.junit.jupiter";
  private static final String MOCKITO_GROUP = "org.mockito";
  private static final VersionSlug SPRING_BOOT_VERSION_SLUG = versionSlug("spring-boot");

  private static final TextNeedleBeforeReplacer DEFAULT_GOAL_REPLACER = new TextNeedleBeforeReplacer(
    notContaining("<defaultGoal>"),
    "</build>"
  );

  private static final SeedDestination MAIN_RESOURCE_DESTINATION = to("src/main/resources");
  private static final SeedDestination TEST_RESOURCES_DESTINATION = to("src/test/resources");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    String packagePath = properties.packagePath();
    SeedDestination testDestination = toSrcTestJava().append(packagePath);
    String fullyQualifiedMainClass = properties.basePackage().get() + "." + baseName + "App";
    String basePackageLoggingLevel = "logging.level.%s".formatted(properties.basePackage().get());

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .and()
      .javaDependencies()
        .removeDependency(dependencyId(JUNIT_GROUP, "junit-jupiter-engine"))
        .removeDependency(dependencyId(JUNIT_GROUP, "junit-jupiter-params"))
        .removeDependency(dependencyId("org.assertj", "assertj-core"))
        .removeDependency(dependencyId(MOCKITO_GROUP, "mockito-junit-jupiter"))
        .addDependencyManagement(springBootBom())
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter"))
        .addDependency(springBootConfigurationProcessor())
        .addDependency(groupId("org.apache.commons"), artifactId("commons-lang3"))
        .addDependency(springBootTest())
        .and()
      .mavenPlugins()
        .pluginManagement(springBootPluginManagement(fullyQualifiedMainClass))
        .plugin(springBootMavenPlugin())
        .and()
      .gradlePlugins()
        .plugin(springBootGradlePlugin(fullyQualifiedMainClass))
        .and()
      .files()
        .add(MAIN_SOURCE.template("MainApp.java"), toSrcMainJava().append(packagePath).append(baseName + "App.java"))
        .add(MAIN_SOURCE.template("ApplicationStartupTraces.java"), toSrcMainJava().append(packagePath).append("ApplicationStartupTraces.java"))
        .add(TEST_SOURCE.template("IntegrationTest.java"), testDestination.append("IntegrationTest.java"))
        .add(MAIN_SOURCE.template("logback-spring.xml"), MAIN_RESOURCE_DESTINATION.append("logback-spring.xml"))
        .add(TEST_SOURCE.template("logback.xml"), TEST_RESOURCES_DESTINATION.append("logback.xml"))
        .add(TEST_SOURCE.template("ApplicationStartupTracesTest.java"), toSrcTestJava().append(packagePath).append("ApplicationStartupTracesTest.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.application.name"), propertyValue(baseName))
        .set(propertyKey(basePackageLoggingLevel), propertyValue("INFO"))
        .and()
      .springLocalProperties()
        .set(propertyKey(basePackageLoggingLevel), propertyValue("DEBUG"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.main.banner-mode"), propertyValue("off"))
        .set(propertyKey("logging.config"), propertyValue("classpath:logback.xml"))
        .and()
      .optionalReplacements()
        .in(path("pom.xml"))
          .add(DEFAULT_GOAL_REPLACER, properties.indentation().times(2) + "<defaultGoal>spring-boot:run</defaultGoal>")
          .and()
        .and()
      .build();
    // @formatter:on
  }

  private GradleMainBuildPlugin springBootGradlePlugin(String fullyQualifiedMainClass) {
    return gradleCommunityPlugin()
      .id("org.springframework.boot")
      .pluginSlug("spring-boot")
      .versionSlug(SPRING_BOOT_VERSION_SLUG)
      .configuration(
        """
        defaultTasks("bootRun")

        springBoot {
          mainClass = "%s"
        }
        """.formatted(fullyQualifiedMainClass)
      )
      .build();
  }

  private JavaDependency springBootBom() {
    return JavaDependency.builder()
      .groupId(SPRING_BOOT_GROUP)
      .artifactId("spring-boot-dependencies")
      .versionSlug(SPRING_BOOT_VERSION_SLUG)
      .type(JavaDependencyType.POM)
      .scope(JavaDependencyScope.IMPORT)
      .build();
  }

  private JavaDependency springBootConfigurationProcessor() {
    return JavaDependency.builder().groupId(SPRING_BOOT_GROUP).artifactId("spring-boot-configuration-processor").optional().build();
  }

  private JavaDependency springBootTest() {
    return JavaDependency.builder()
      .groupId(SPRING_BOOT_GROUP)
      .artifactId("spring-boot-starter-test")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private MavenPlugin springBootPluginManagement(String fullyQualifiedMainClass) {
    return MavenPlugin.builder()
      .groupId(SPRING_BOOT_GROUP)
      .artifactId("spring-boot-maven-plugin")
      .versionSlug(SPRING_BOOT_VERSION_SLUG)
      .addExecution(pluginExecution().goals("repackage"))
      .configuration("<mainClass>%s</mainClass>".formatted(fullyQualifiedMainClass))
      .build();
  }

  private MavenPlugin springBootMavenPlugin() {
    return mavenPlugin().groupId(SPRING_BOOT_GROUP).artifactId("spring-boot-maven-plugin").build();
  }
}
