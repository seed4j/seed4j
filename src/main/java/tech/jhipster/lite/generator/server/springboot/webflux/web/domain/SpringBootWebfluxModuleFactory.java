package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringBootWebfluxModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/webflux/web");

  private static final PropertyKey SERVER_PORT = propertyKey("server.port");
  private static final PropertyKey EXCEPTION_PACKAGE = propertyKey("application.exception.package");

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final String EXCEPTION_PRIMARY = "technical/infrastructure/primary/exception";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-webflux"))
        .addDependency(reactorTestDependency())
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-validation"))
        .and()
      .springMainProperties()
        .set(SERVER_PORT, propertyValue(properties.serverPort().stringValue()))
        .set(propertyKey("application.exception.details"), propertyValue("false"))
        .set(EXCEPTION_PACKAGE, propertyValue("org.", "java.", "net.", "javax.", "com.", "io.", "de.", properties.basePackage().get()))
        .and()
      .springTestProperties()
        .set(SERVER_PORT, propertyValue("0"))
        .set(EXCEPTION_PACKAGE, propertyValue("org.", "java."))
        .and()
      .files()
        .batch(SOURCE.append("main"), toSrcMainJava().append(packagePath).append(EXCEPTION_PRIMARY))
          .addTemplate("GlobalErrorWebExceptionHandler.java")
          .addTemplate("ErrorDTO.java")
          .and()
        .batch(SOURCE.append("test"), toSrcTestJava().append(packagePath).append(EXCEPTION_PRIMARY))
          .addTemplate("GlobalErrorWebExceptionHandlerIT.java")
          .addTemplate("GlobalErrorWebExceptionHandlerTestController.java")
          .and()
        .add(SOURCE.template("test/TestUtil.java"), toSrcTestJava().append(packagePath).append("TestUtil.java"))
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency reactorTestDependency() {
    return javaDependency().groupId("io.projectreactor").artifactId("reactor-test").scope(JavaDependencyScope.TEST).build();
  }
}
