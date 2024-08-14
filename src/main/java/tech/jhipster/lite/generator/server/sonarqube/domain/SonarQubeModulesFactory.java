package tech.jhipster.lite.generator.server.sonarqube.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.mavenplugin.MavenBuildPhase.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityPlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradleMainBuildPlugin;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SonarQubeModulesFactory {

  private static final JHipsterSource SOURCE = from("server/sonar");
  private static final JHipsterDestination SONAR_PROPERTIES_DESTINATION = to("sonar-project.properties");
  private static final String SONARQUBE = "sonarqube";

  private final DockerImages dockerImages;

  public SonarQubeModulesFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildBackendModule(JHipsterModuleProperties properties) {
    return commonModuleFiles(properties)
      .files()
      .add(SOURCE.template("sonar-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  public JHipsterModule buildBackendFrontendModule(JHipsterModuleProperties properties) {
    return commonModuleFiles(properties)
      .files()
      .add(SOURCE.template("sonar-fullstack-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  private JHipsterModuleBuilder commonModuleFiles(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("sonarqubeDockerImage", dockerImages.get(SONARQUBE).fullName())
        .and()
      .documentation(documentationTitle("sonar"), SOURCE.template("sonar.md"))
      .sonarAnalysisCommands()
        .dockerCompose("src/main/docker/sonar.yml")
        .maven("clean verify sonar:sonar")
        .gradle("clean build sonar --info")
        .and()
      .mavenPlugins()
        .pluginManagement(propertiesPlugin())
        .plugin(propertiesPluginBuilder().build())
        .pluginManagement(sonarPlugin())
        .and()
      .gradlePlugins()
        .plugin(gradleSonarPlugin())
        .and()
      .files()
        .add(SOURCE.template("sonar.yml"), toSrcMainDocker().append("sonar.yml"))
        .and();
    //@formatter:on
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
    String configuration =
      """
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
