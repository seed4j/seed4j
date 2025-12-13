package com.seed4j.generator.server.springboot.mvc.web.domain;

import static com.seed4j.module.domain.Seed4JModule.*;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootMvcModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";
  private static final String CORS = "cors";

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/web");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final Seed4JSource JACKSON_MAIN_SOURCE = from("server/springboot/jackson/main");
  private static final Seed4JSource JACKSON_TEST_SOURCE = from("server/springboot/jackson/test");
  private static final String WIRE_JACKSON_CONFIG = "wire/jackson/infrastructure/primary";

  private static final GroupId SPRING_BOOT_GROUP = groupId("org.springframework.boot");
  private static final ArtifactId STARTER_WEB_ARTIFACT_ID = artifactId("spring-boot-starter-web");
  private static final ArtifactId STARTER_WEBMVC_TEST_ARTIFACT_ID = artifactId("spring-boot-starter-webmvc-test");

  private static final PropertyKey SERVER_PORT = propertyKey("server.port");

  private static final String CORS_DESTINATION = "wire/security";
  private static final String CORS_PRIMARY = CORS_DESTINATION + "/infrastructure/primary";

  public Seed4JModule buildEmptyModule(Seed4JModuleProperties properties) {
    return moduleBuilder(properties).build();
  }

  public Seed4JModule buildTomcatModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return springMvcBuilder(properties, "org.springframework.web", LogLevel.ERROR)
      .javaDependencies()
        .addDependency(SPRING_BOOT_GROUP, STARTER_WEB_ARTIFACT_ID)
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-tomcat"))
        .addTestDependency(SPRING_BOOT_GROUP, STARTER_WEBMVC_TEST_ARTIFACT_ID, versionSlug("spring-boot"))
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-jackson2"))
        .and()
      .build();
    // @formatter:on
  }

  private Seed4JModuleBuilder springMvcBuilder(Seed4JModuleProperties properties, String loggerName, LogLevel logLevel) {
    String packagePath = properties.packagePath();

    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("CORS configuration"), SOURCE.file("cors-configuration.md"))
      .localEnvironment(localEnvironment("- [Local server](http://localhost:"+properties.serverPort().get()+")"))
      .javaDependencies()
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-validation"))
        .addDependency(reflectionsDependency())
        .and()
      .springMainProperties()
        .set(SERVER_PORT, propertyValue(properties.serverPort().get()))
        .set(propertyKey("spring.jackson.default-property-inclusion"), propertyValue("non_absent"))
        .and()
      .springTestProperties()
        .set(SERVER_PORT, propertyValue(0))
        .and()
      .files()
        .add(JACKSON_MAIN_SOURCE.append(WIRE_JACKSON_CONFIG).template("JacksonConfiguration.java"), toSrcMainJava().append(packagePath).append(WIRE_JACKSON_CONFIG).append("JacksonConfiguration.java"))
        .add(JACKSON_TEST_SOURCE.append(WIRE_JACKSON_CONFIG).template("JacksonConfigurationIT.java"), toSrcTestJava().append(packagePath).append(WIRE_JACKSON_CONFIG).append("JacksonConfigurationIT.java"))
        .add(SOURCE.file("resources/404.html"), to("src/main/resources/public/error/404.html"))
        .batch(MAIN_SOURCE.append(CORS), mainDestination.append(CORS_PRIMARY))
          .addTemplate("CorsFilterConfiguration.java")
          .addTemplate("CorsProperties.java")
          .and()
        .add(MAIN_SOURCE.append(CORS).template(PACKAGE_INFO), mainDestination.append(CORS_DESTINATION).append(PACKAGE_INFO))
        .add(
          TEST_SOURCE.append(CORS).template("CorsFilterConfigurationIT.java"),
          testDestination.append(CORS_PRIMARY).append("CorsFilterConfigurationIT.java")
        )
        .add(TEST_SOURCE.template("JsonHelper.java"), testDestination.append("JsonHelper.java"))
        .batch(TEST_SOURCE, toSrcTestJava().append(properties.packagePath()))
          .addTemplate("BeanValidationAssertions.java")
          .addTemplate("BeanValidationTest.java")
        .and()
        .add(MAIN_SOURCE.template("BeanValidationErrorsHandler.java"), mainDestination.append("shared/error/infrastructure/primary/BeanValidationErrorsHandler.java"))
        .batch(TEST_SOURCE, testDestination.append("shared/error/infrastructure/primary"))
          .addTemplate("BeanValidationErrorsHandlerIT.java")
          .addTemplate("BeanValidationErrorsHandlerTest.java")
          .and()
        .batch(TEST_SOURCE, testDestination.append("shared/error_generator/infrastructure/primary"))
          .addTemplate("BeanValidationErrorsResource.java")
          .addTemplate("RestMandatoryParameter.java")
          .and()
      .and()
      .springTestLogger(loggerName, logLevel)
      .springMainLogger(loggerName, logLevel);
    // @formatter:on
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency()
      .groupId("org.reflections")
      .artifactId("reflections")
      .versionSlug("reflections")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
