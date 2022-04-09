package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS;
import static tech.jhipster.lite.generator.tools.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.List;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SpringBootMvcAssertFiles {

  public static List<String> springBootStarterWebDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-web</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> springBootStarterUndertowDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-undertow</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> springBootStarterActuatorDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-actuator</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> springBootStarterWebWithoutTomcat() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-web</artifactId>",
      "<exclusions>",
      "<exclusion>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-tomcat</artifactId>",
      "</exclusion>",
      "</exclusions>",
      "</dependency>"
    );
  }

  public static List<String> zalandoProblemDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.zalando</groupId>",
      "<artifactId>problem-spring-web</artifactId>",
      "<version>${problem-spring-web.version}</version>",
      "</dependency>"
    );
  }

  public static List<String> springBootStarterValidationDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-validation</artifactId>",
      "</dependency>"
    );
  }

  public static void assertCorsFiles(Project project) {
    String corsPath = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH), TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS);
    assertFileExist(project, getPath(MAIN_JAVA, corsPath, "CorsFilterConfiguration.java"));
    assertFileExist(project, getPath(MAIN_JAVA, corsPath, "CorsProperties.java"));
    assertFileExist(project, getPath(TEST_JAVA, corsPath, "CorsFilterConfigurationIT.java"));
  }

  public static void assertCorsProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    List<String> corsProperties = List.of(
      "application.cors.allowed-origins=http://localhost:8100,http://localhost:9000",
      "application.cors.allowed-methods=*",
      "application.cors.allowed-headers=*",
      "application.cors.exposed-headers=Authorization,Link,X-Total-Count,X-" +
      baseName +
      "-alert,X-" +
      baseName +
      "-error,X-" +
      baseName +
      "-params",
      "application.cors.allow-credentials=true",
      "application.cors.max-age=1800",
      "application.cors.allowed-origin-patterns=https://*.githubpreview.dev"
    );

    List<String> commentedCorsProperties = corsProperties.stream().map(p -> "# " + p).toList();

    assertFileContent(project, getPath(MAIN_RESOURCES, CONFIG, "application.properties"), commentedCorsProperties);
    assertFileContent(project, getPath(TEST_RESOURCES, CONFIG, "application.properties"), corsProperties);
    assertFileContent(project, getPath(MAIN_RESOURCES, CONFIG, "application-local.properties"), corsProperties);
  }
}
