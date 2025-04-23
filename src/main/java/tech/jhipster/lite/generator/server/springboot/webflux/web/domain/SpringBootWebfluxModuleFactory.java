package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.artifactId;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.groupId;
import static tech.jhipster.lite.module.domain.JHipsterModule.javaDependency;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcMainJava;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcTestJava;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringBootWebfluxModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/webflux/web");
  private static final JHipsterSource JACKSON_MAIN_SOURCE = from("server/springboot/jackson/main");
  private static final JHipsterSource JACKSON_TEST_SOURCE = from("server/springboot/jackson/test");
  private static final String WIRE_JACKSON_CONFIG = "wire/jackson/infrastructure/primary";
  private static final PropertyKey SERVER_PORT = propertyKey("server.port");

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final String EXCEPTION_PRIMARY = "shared/error/infrastructure/primary";

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return moduleBuilder(properties).build();
  }

  public JHipsterModule buildNettyModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-webflux"))
        .addDependency(reactorTestDependency())
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-validation"))
        .and()
      .springMainProperties()
        .set(SERVER_PORT, propertyValue(properties.serverPort().get()))
        .set(propertyKey("server.forward-headers-strategy"), propertyValue("FRAMEWORK"))
        .set(propertyKey("spring.jackson.default-property-inclusion"), propertyValue("non_absent"))
        .and()
      .springTestProperties()
        .set(SERVER_PORT, propertyValue(0))
        .and()
      .files()
        .add(JACKSON_MAIN_SOURCE.append(WIRE_JACKSON_CONFIG).template("JacksonConfiguration.java"), toSrcMainJava().append(packagePath).append(WIRE_JACKSON_CONFIG).append("JacksonConfiguration.java"))
        .add(JACKSON_TEST_SOURCE.append(WIRE_JACKSON_CONFIG).template("JacksonConfigurationIT.java"), toSrcTestJava().append(packagePath).append(WIRE_JACKSON_CONFIG).append("JacksonConfigurationIT.java"))
        .batch(SOURCE.append("main"), toSrcMainJava().append(packagePath).append(EXCEPTION_PRIMARY))
          .addTemplate("FieldErrorDTO.java")
          .addTemplate("HeaderUtil.java")
          .and()
        .batch(SOURCE.append("test"), toSrcTestJava().append(packagePath).append(EXCEPTION_PRIMARY))
          .addTemplate("HeaderUtilTest.java")
          .addTemplate("FieldErrorDTOTest.java")
          .and()
        .add(SOURCE.template("test/TestUtil.java"), toSrcTestJava().append(packagePath).append("TestUtil.java"))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency reactorTestDependency() {
    return javaDependency().groupId("io.projectreactor").artifactId("reactor-test").scope(JavaDependencyScope.TEST).build();
  }
}
