package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.assertCorsFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.assertCorsProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterActuatorDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterUndertowDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterValidationDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterWebDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterWebWithoutTomcat;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.zalandoProblemDependency;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootMvcApplicationServiceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootMvcApplicationService springBootMvcApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.init(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvc() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvcWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 7419);
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=7419");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvcWithInvalidServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", "chips");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootUndertow() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootUndertow(project);

    assertUndertow(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"io.undertow\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootUndertowWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 1664);
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootUndertow(project);

    assertUndertow(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=1664");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"io.undertow\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootActuator() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootActuator(project);

    assertFileContent(project, POM_XML, springBootStarterActuatorDependency());

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "management.endpoints.web.base-path=/management");
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "management.endpoints.web.exposure.include=configprops, env, health, info, logfile, loggers, threaddump"
    );
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "management.endpoint.health.probes.enabled=true");
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "management.endpoint.health.group.liveness.include=livenessState"
    );
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "management.endpoint.health.group.readiness.include=readinessState"
    );
  }

  @Test
  void shouldAddExceptionHandler() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addExceptionHandler(project);

    assertTestUtil(project);
    assertExceptionHandler(project);
  }

  private void assertExceptionHandler(Project project) {
    assertFileContent(project, POM_XML, "<problem-spring.version>");
    assertFileContent(project, POM_XML, "<problem-spring-web.version>");

    assertZalandoProblem(project);
    assertSpringBootStarterValidation(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
  }

  private void assertCors(Project project) {
    assertCorsFiles(project);
    assertCorsProperties(project);
  }

  private void assertTomcat(Project project) {
    assertFileContent(project, POM_XML, springBootStarterWebDependency());
  }

  private void assertUndertow(Project project) {
    assertFileContent(project, POM_XML, springBootStarterWebWithoutTomcat());
    assertFileContent(project, POM_XML, springBootStarterUndertowDependency());
  }

  private void assertZalandoProblem(Project project) {
    assertFileContent(project, POM_XML, zalandoProblemDependency());
  }

  private void assertSpringBootStarterValidation(Project project) {
    assertFileContent(project, POM_XML, springBootStarterValidationDependency());
  }

  private void assertExceptionHandlerProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "/config/application.properties"),
      List.of(
        "application.exception.details=false",
        "application.exception.package=org.,java.,net.,javax.,com.,io.,de.,com.mycompany.myapp"
      )
    );
    assertFileContent(project, getPath(TEST_RESOURCES, "/config/application.properties"), "application.exception.package=org.,java.");
  }

  private void assertExceptionHandlerFiles(Project project) {
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
      "package com.mycompany.myapp.technical.infrastructure.primary.exception;"
    );

    List<String> listTestClass = List.of(
      "BadRequestAlertExceptionTest.java",
      "ExceptionTranslatorIT.java",
      "ExceptionTranslatorTestController.java",
      "FieldErrorDTOTest.java",
      "HeaderUtilTest.java"
    );
    listTestClass.forEach(testClass -> assertFileExist(project, getPath(TEST_JAVA, packagePath), testClass));
  }

  private void assertTestUtil(Project project) {
    assertFileExist(project, getPath(TEST_JAVA, project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH)), "TestUtil.java");
  }

  public void assertLoggingConfiguration(Project project, String loggerEntry) {
    assertFileContent(project, getPath(MAIN_RESOURCES, "logback-spring.xml"), loggerEntry);
    assertFileContent(project, getPath(TEST_RESOURCES, "logback.xml"), loggerEntry);
  }
}
