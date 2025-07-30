package com.seed4j.generator.server.springboot.mvc.web.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.localEnvironment;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootMvcModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";
  private static final String CORS = "cors";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/web");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final JHipsterSource JACKSON_MAIN_SOURCE = from("server/springboot/jackson/main");
  private static final JHipsterSource JACKSON_TEST_SOURCE = from("server/springboot/jackson/test");
  private static final String WIRE_JACKSON_CONFIG = "wire/jackson/infrastructure/primary";

  private static final GroupId SPRING_BOOT_GROUP = groupId("org.springframework.boot");
  private static final ArtifactId STARTER_WEB_ARTIFACT_ID = artifactId("spring-boot-starter-web");

  private static final PropertyKey SERVER_PORT = propertyKey("server.port");

  private static final String CORS_DESTINATION = "wire/security";
  private static final String CORS_PRIMARY = CORS_DESTINATION + "/infrastructure/primary";

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return moduleBuilder(properties).build();
  }

  public JHipsterModule buildTomcatModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return springMvcBuilder(properties, "org.springframework.web", LogLevel.ERROR)
      .javaDependencies()
        .addDependency(SPRING_BOOT_GROUP, STARTER_WEB_ARTIFACT_ID)
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildUndertowModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return springMvcBuilder(properties, "io.undertow", LogLevel.WARN)
      .javaDependencies()
        .addDependency(springBootWebWithoutTomcatDependency())
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-undertow"))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency springBootWebWithoutTomcatDependency() {
    return javaDependency()
      .groupId(SPRING_BOOT_GROUP)
      .artifactId(STARTER_WEB_ARTIFACT_ID)
      .addExclusion(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-tomcat"))
      .build();
  }

  private JHipsterModuleBuilder springMvcBuilder(JHipsterModuleProperties properties, String loggerName, LogLevel logLevel) {
    String packagePath = properties.packagePath();

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath);

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
