package com.seed4j.generator.ci.sonarqube.domain;

import static com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.pluginExecution;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainDocker;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.INITIALIZE;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SonarQubeModuleFactory {

  private static final String PROPERTIES = "properties";
  private static final SeedSource SOURCE = from("ci/sonarqube");
  private static final SeedDestination SONAR_PROPERTIES_DESTINATION = to("sonar-project.properties");
  private static final String SONARQUBE = "sonarqube";

  private final DockerImages dockerImages;

  public SonarQubeModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildBackendModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return commonBackendModuleFiles(properties)
      .files()
      .add(SOURCE.template("server/sonar-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  public JHipsterModule buildBackendFrontendModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return commonBackendModuleFiles(properties)
      .files()
      .add(SOURCE.template("server/sonar-fullstack-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  public JHipsterModule buildTypescriptModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return commonModuleFiles(properties)
      .documentation(documentationTitle("sonar"), SOURCE.template("typescript/sonar.md"))
      .packageJson()
        .addDevDependency(packageName("@sonar/scan"), COMMON)
        .and()
      .files()
        .add(SOURCE.template("typescript/sonar-project.properties"), SONAR_PROPERTIES_DESTINATION)
        .and()
      .build();
    // @formatter:on
  }

  private SeedModuleBuilder commonModuleFiles(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("sonarqubeDockerImage", dockerImages.get(SONARQUBE).fullName())
        .and()
      .files()
        .add(SOURCE.template("sonar.yml"), toSrcMainDocker().append("sonar.yml"))
        .and()
      .files()
        .add(SOURCE.append("sonar/Dockerfile"), toSrcMainDocker().append("sonar/Dockerfile"))
        .and()
      .files()
        .add(SOURCE.append("sonar/sonar_generate_token.sh"), toSrcMainDocker().append("sonar/sonar_generate_token.sh"))
        .and();
    // @formatter:on
  }

  private SeedModuleBuilder commonBackendModuleFiles(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return commonModuleFiles(properties)
      .documentation(documentationTitle("sonar"), SOURCE.template("sonar.md"))
      .mavenPlugins()
        .pluginManagement(propertiesPlugin())
        .plugin(propertiesPluginBuilder().build())
        .pluginManagement(sonarPlugin())
        .and()
      .gradlePlugins()
        .plugin(gradleSonarPlugin())
        .and();
    // @formatter:on
  }

  private MavenPlugin propertiesPlugin() {
    return propertiesPluginBuilder()
      .versionSlug("properties-maven-plugin")
      .addExecution(
        pluginExecution()
          .goals("read-project-properties")
          .id("default-cli")
          .phase(INITIALIZE)
          .configuration(
            """
            <files>
              <file>sonar-project.properties</file>
            </files>
            """
          )
      )
      .build();
  }

  private static MavenPlugin.MavenPluginOptionalBuilder propertiesPluginBuilder() {
    return MavenPlugin.builder().groupId("org.codehaus.mojo").artifactId("properties-maven-plugin");
  }

  private MavenPlugin sonarPlugin() {
    return MavenPlugin.builder()
      .groupId("org.sonarsource.scanner.maven")
      .artifactId("sonar-maven-plugin")
      .versionSlug("sonar-maven-plugin")
      .build();
  }

  private GradleMainBuildPlugin gradleSonarPlugin() {
    String configuration = """
      val sonarProperties = Properties()
      File("sonar-project.properties").inputStream().use { inputStream ->
          sonarProperties.load(inputStream)
      }

      sonarqube {
          properties {
            sonarProperties
              .map { it -> it.key as String to (it.value as String).split(",").map { it.trim() } }
              .forEach { (key, values) -> property(key, values) }
            property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
            property("sonar.junit.reportPaths", "build/test-results/test,build/test-results/integrationTest")
          }
      }
      """;

    return GradleCommunityPlugin.builder()
      .id("org.sonarqube")
      .pluginSlug(SONARQUBE)
      .versionSlug(SONARQUBE)
      .withBuildGradleImport("java.util.Properties")
      .configuration(configuration)
      .build();
  }
}
