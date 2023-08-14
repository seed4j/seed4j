package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringBootMvcsModulesFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/web");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId SPRING_BOOT_GROUP = groupId("org.springframework.boot");
  private static final ArtifactId STARTER_WEB_ARTIFACT_ID = artifactId("spring-boot-starter-web");

  private static final PropertyKey SERVER_PORT = propertyKey("server.port");

  private static final String CORS_PRIMARY = "security/infrastructure/primary";

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return moduleBuilder(properties).build();
  }

  public JHipsterModule buildTomcatModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return springMvcBuilder(properties, "org.springframework.web", LogLevel.ERROR)
      .javaDependencies()
        .addDependency(SPRING_BOOT_GROUP, STARTER_WEB_ARTIFACT_ID)
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildUndertowModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return springMvcBuilder(properties, "io.undertow", LogLevel.WARN)
      .javaDependencies()
        .addDependency(springBootWebWithoutTomcatDependency())
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-undertow"))
        .and()
      .build();
    //@formatter:on
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

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("CORS configuration"), SOURCE.file("cors-configuration.md"))
      .localEnvironment(localEnvironment("- [Local server](http://localhost:"+properties.serverPort().get()+")"))
      .javaDependencies()
        .addDependency(SPRING_BOOT_GROUP, artifactId("spring-boot-starter-validation"))
        .addDependency(reflectionsDependency())
        .and()
      .springMainProperties()
        .set(SERVER_PORT, propertyValue(properties.serverPort().stringValue()))
        .and()
      .springTestProperties()
        .set(SERVER_PORT, propertyValue("0"))
        .and()
      .files()
        .add(SOURCE.file("resources/404.html"), to("src/main/resources/public/error/404.html"))
        .batch(MAIN_SOURCE.append("cors"), toSrcMainJava().append(packagePath).append(CORS_PRIMARY))
          .addTemplate("CorsFilterConfiguration.java")
          .addTemplate("CorsProperties.java")
          .and()
        .add(
          TEST_SOURCE.append("cors").template("CorsFilterConfigurationIT.java"),
          toSrcTestJava().append(packagePath).append(CORS_PRIMARY).append("CorsFilterConfigurationIT.java")
        )
        .add(TEST_SOURCE.template("JsonHelper.java"), toSrcTestJava().append(packagePath).append("JsonHelper.java"))
        .batch(TEST_SOURCE, toSrcTestJava().append(properties.packagePath()))
          .addTemplate("BeanValidationAssertions.java")
          .addTemplate("BeanValidationTest.java")
        .and()
        .add(MAIN_SOURCE.template("BeanValidationErrorsHandler.java"), toSrcMainJava().append(packagePath).append("shared/error/infrastructure/primary/BeanValidationErrorsHandler.java"))
        .batch(TEST_SOURCE, toSrcTestJava().append(packagePath).append("shared/error/infrastructure/primary"))
          .addTemplate("BeanValidationErrorsHandlerIntTest.java")
          .addTemplate("BeanValidationErrorsHandlerTest.java")
          .and()
        .batch(TEST_SOURCE, toSrcTestJava().append(packagePath).append("shared/error_generator/infrastructure/primary"))
          .addTemplate("BeanValidationErrorsResource.java")
          .addTemplate("RestMandatoryParameter.java")
          .and()
      .and()
      .springTestLogger(loggerName, logLevel)
      .springMainLogger(loggerName, logLevel);
    //@formatter:on
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
