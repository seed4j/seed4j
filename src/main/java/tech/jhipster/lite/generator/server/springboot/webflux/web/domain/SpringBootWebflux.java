package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import java.util.List;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class SpringBootWebflux {

  public static final String SPRINGBOOT_PACKAGE = "org.springframework.boot";

  private SpringBootWebflux() {}

  public static Dependency springBootStarterWebfluxDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-webflux").build();
  }

  public static Dependency problemSpringWebfluxDependency() {
    return Dependency
      .builder()
      .groupId("org.zalando")
      .artifactId("problem-spring-webflux")
      .version("\\${problem-spring-webflux.version}")
      .build();
  }

  public static Dependency springBootStarterValidation() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-validation").build();
  }

  public static Dependency springBootActuatorDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-actuator").build();
  }

  public static Dependency reactorTestDependency() {
    return Dependency.builder().groupId("io.projectreactor").artifactId("reactor-test").scope("test").build();
  }

  public static List<String> srcJavaFiles() {
    return List.of(
      "ProblemConfiguration.java",
      "HeaderUtil.java",
      "BadRequestAlertException.java",
      "ErrorConstants.java",
      "ExceptionTranslator.java",
      "FieldErrorDTO.java"
    );
  }

  public static List<String> testJavaFiles() {
    return List.of(
      "HeaderUtilTest.java",
      "BadRequestAlertExceptionTest.java",
      "ExceptionTranslatorIT.java",
      "ExceptionTranslatorTestController.java",
      "FieldErrorDTOTest.java"
    );
  }
}
