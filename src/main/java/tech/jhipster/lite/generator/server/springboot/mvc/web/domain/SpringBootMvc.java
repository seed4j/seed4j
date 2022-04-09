package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;

import java.util.LinkedHashMap;
import java.util.Map;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class SpringBootMvc {

  public static final String SPRINGBOOT_PACKAGE = "org.springframework.boot";

  public static final String CORS = "cors";
  public static final String TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS = getPath(TECHNICAL_INFRASTRUCTURE_PRIMARY, CORS);

  private SpringBootMvc() {}

  public static Dependency springBootStarterWebDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-web").build();
  }

  public static Dependency springBootActuatorDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-actuator").build();
  }

  public static Dependency tomcatDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-tomcat").build();
  }

  public static Dependency undertowDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-undertow").build();
  }

  public static Dependency problemSpringDependency() {
    return Dependency.builder().groupId("org.zalando").artifactId("problem-spring-web").version("\\${problem-spring-web.version}").build();
  }

  public static Dependency springBootStarterValidation() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter-validation").build();
  }

  public static Map<String, String> corsFiles() {
    return Map.of(
      "CorsFilterConfiguration.java",
      TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS,
      "CorsProperties.java",
      TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS
    );
  }

  public static Map<String, Object> corsProperties(String baseName) {
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("application.cors.allowed-origins", "http://localhost:8100,http://localhost:9000");
    result.put("application.cors.allowed-methods", "*");
    result.put("application.cors.allowed-headers", "*");
    result.put(
      "application.cors.exposed-headers",
      "Authorization,Link,X-Total-Count,X-" + baseName + "-alert,X-" + baseName + "-error,X-" + baseName + "-params"
    );
    result.put("application.cors.allow-credentials", "true");
    result.put("application.cors.max-age", "1800");
    result.put("application.cors.allowed-origin-patterns", "https://*.githubpreview.dev");
    return result;
  }
}
