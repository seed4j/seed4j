package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaClient;

public class EurekaAssert {

  public static void assertDependencies(Project project) {
    assertFileContent(project, "pom.xml", "<spring-cloud.version>");
    assertFileContent(project, "pom.xml", "<spring-cloud-netflix-eureka-client.version>");

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-dependencies</artifactId>",
        "<version>${spring-cloud.version}</version>",
        "<scope>import</scope>",
        "<type>pom</type>",
        "</dependency>"
      )
    );

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-bootstrap</artifactId>",
        "</dependency>"
      )
    );

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>",
        "<version>${spring-cloud-netflix-eureka-client.version}</version>",
        "</dependency>"
      )
    );
  }

  public static void assertProperties(Project project) {
    assertFileExist(project, MAIN_RESOURCES, "config/bootstrap.properties");
    assertFileExist(project, TEST_RESOURCES, "config/bootstrap.properties");

    String baseName = project.getBaseName().orElseThrow();
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap.properties"),
      List.of(
        "spring.application.name=" + baseName,
        "eureka.client.service-url.defaultZone=http://admin:admin@localhost:8761/eureka",
        "eureka.client.enabled=true",
        "eureka.client.healthcheck.enabled=true",
        "eureka.client.fetch-registry=true",
        "eureka.client.register-with-eureka=true",
        "eureka.client.instance-info-replication-interval-seconds=10",
        "eureka.client.registry-fetch-interval-seconds=10",
        "eureka.instance.appname=" + baseName,
        "eureka.instance.instance-id=" + baseName + ":${spring.application.instance-id:${random.value}}",
        "eureka.instance.lease-renewal-interval-in-seconds=5",
        "eureka.instance.lease-expiration-duration-in-seconds=10",
        "eureka.instance.status-page-url-path=${management.endpoints.web.base-path}/info",
        "eureka.instance.health-check-url-path=${management.endpoints.web.base-path}/health"
      )
    );

    assertFileContent(project, getPath(TEST_RESOURCES, "config/bootstrap.properties"), List.of("eureka.client.enabled=false"));
  }

  public static void assertDockerCompose(Project project) {
    assertFileExist(project, "src/main/docker/jhipster-registry.yml");
    assertFileContent(project, "src/main/docker/jhipster-registry.yml", EurekaClient.JHIPSTER_REGISTRY_DOCKER_IMAGE);
    assertFileExist(project, "src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
