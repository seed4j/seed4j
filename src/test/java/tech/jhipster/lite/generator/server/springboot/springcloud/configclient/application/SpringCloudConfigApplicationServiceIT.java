package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringCloudConfigApplicationServiceIT {

  @Autowired
  SpringCloudConfigClientApplicationService springCloudConfigClientApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springCloudConfigClientApplicationService.init(project);

    assertDependencies(project);

    assertDockerCompose(project);

    assertProperties(project);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springCloudConfigClientApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddDockerCompose() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springCloudConfigClientApplicationService.addDockerCompose(project);

    assertDockerCompose(project);
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springCloudConfigClientApplicationService.addProperties(project);

    assertProperties(project);
  }

  private void assertDependencies(Project project) {
    assertFileContent(project, "pom.xml", springCloudConfigClient());
    assertFileContent(project, "pom.xml", springCloudBootstrap());
    assertFileContent(project, "pom.xml", springCloudDependencies());

    assertFileContent(project, POM_XML, "<spring-cloud.version>");
    assertFileContent(project, POM_XML, "</spring-cloud.version>");
  }

  private void assertDockerCompose(Project project) {
    assertFileExist(project, "src/main/docker/jhipster-registry.yml");
    assertFileContent(project, "src/main/docker/jhipster-registry.yml", "jhipster/jhipster-registry:v7.3.0");
    assertFileContent(project, "src/main/docker/jhipster-registry.yml", "JHIPSTER_REGISTRY_PASSWORD=admin");
    assertFileExist(project, "src/main/docker/central-server-config/localhost-config/application.properties");
  }

  private void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap.properties"),
      bootstrapProperties(project.getBaseName().orElse("jhipster"))
    );

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/bootstrap.properties"), List.of("spring.cloud.config.fail-fast=true"));

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap-local.properties"),
      bootstrapProperties(project.getBaseName().orElse("jhipster"))
    );

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap-local.properties"),
      List.of("spring.cloud.config.fail-fast=false")
    );

    assertFileContent(project, getPath(TEST_RESOURCES, "config/bootstrap.properties"), "spring.cloud.config.enabled=false");
  }

  private List<String> bootstrapProperties(String baseName) {
    List<String> properties = new ArrayList<>();
    properties.add("jhipster.registry.password=admin");
    properties.add("spring.cloud.config.label=main");
    properties.add("spring.cloud.config.name=" + baseName);
    properties.add("spring.cloud.config.retry.initial-interval=1000");
    properties.add("spring.cloud.config.retry.max-attempts=100");
    properties.add("spring.cloud.config.retry.max-interval=2000");
    properties.add("spring.cloud.config.uri=http://admin:${jhipster.registry.password}@localhost:8761/config");
    return properties;
  }

  private List<String> springCloudConfigClient() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.cloud</groupId>",
      "<artifactId>spring-cloud-starter-config</artifactId>",
      "</dependency>"
    );
  }

  private List<String> springCloudBootstrap() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.cloud</groupId>",
      "<artifactId>spring-cloud-starter-bootstrap</artifactId>",
      "</dependency>"
    );
  }

  private List<String> springCloudDependencies() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.cloud</groupId>",
      "<artifactId>spring-cloud-dependencies</artifactId>",
      "<version>${spring-cloud.version}</version>",
      "<scope>import</scope>",
      "<type>pom</type>",
      "</dependency>"
    );
  }
}
