package tech.jhipster.lite.generator.server.springboot.webflux.web.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.project.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;

public class SpringbootWebfluxAssert {

  public static void assertDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-webflux</artifactId>",
        "</dependency>"
      )
    );
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>io.projectreactor</groupId>",
        "<artifactId>reactor-test</artifactId>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  public static void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, CONFIG, "application.properties"),
      List.of("# Spring Boot Webflux", "server.port=8080", "")
    );
    assertFileContent(project, getPath(TEST_RESOURCES, CONFIG, "application.properties"), List.of("server.port=0"));
  }

  public static void assertExceptionHandlerDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.zalando</groupId>",
        "<artifactId>problem-spring-webflux</artifactId>",
        "<version>${problem-spring-webflux.version}</version>",
        "</dependency>"
      )
    );
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-validation</artifactId>",
        "</dependency>"
      )
    );
  }

  public static void assertExceptionHandlerProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "/config/application.properties"),
      List.of(
        "application.exception.details=false",
        "application.exception.package=org.,java.,net.,javax.,com.,io.,de.," + project.getPackageName().orElseThrow(),
        ""
      )
    );
    assertFileContent(project, getPath(TEST_RESOURCES, "/config/application.properties"), "application.exception.package=org.,java.");
  }

  public static void assertExceptionHandlerFiles(Project project) {
    String packagePath = getPath(
      project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH),
      TECHNICAL_INFRASTRUCTURE_PRIMARY,
      "exception"
    );
    List<String> listClass = List.of(
      "BadRequestAlertException.java",
      "ErrorConstants.java",
      "ExceptionTranslator.java",
      "FieldErrorDTO.java",
      "HeaderUtil.java",
      "ProblemConfiguration.java"
    );
    listClass.forEach(javaClass -> assertFileExist(project, getPath(MAIN_JAVA, packagePath), javaClass));
    assertFileContent(
      project,
      getPath(MAIN_JAVA, packagePath, "ExceptionTranslator.java"),
      "package " + project.getPackageName().orElseThrow() + ".technical.infrastructure.primary.exception;"
    );

    List<String> listTestClass = List.of(
      "BadRequestAlertExceptionTest.java",
      "ExceptionTranslatorIT.java",
      "ExceptionTranslatorTestController.java",
      "FieldErrorDTOTest.java",
      "HeaderUtilTest.java"
    );
    listTestClass.forEach(testClass -> assertFileExist(project, getPath(TEST_JAVA, packagePath), testClass));

    assertFileExist(project, getPath(TEST_JAVA, project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH)), "TestUtil.java");
  }

  public static void assertActuatorDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-actuator</artifactId>",
        "</dependency>"
      )
    );
  }

  public static void assertActuatorProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "/config/application.properties"),
      List.of(
        "Spring Boot Actuator",
        "management.endpoints.web.base-path=/management",
        "management.endpoints.web.exposure.include=configprops, env, health, info, logfile, loggers, threaddump",
        "management.endpoint.health.probes.enabled=true",
        ""
      )
    );
  }
}
