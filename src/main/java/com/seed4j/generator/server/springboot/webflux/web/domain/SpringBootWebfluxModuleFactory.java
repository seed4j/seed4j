package com.seed4j.generator.server.springboot.webflux.web.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootWebfluxModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/webflux/web");
  private static final SeedSource JACKSON_MAIN_SOURCE = from("server/springboot/jackson/main");
  private static final SeedSource JACKSON_TEST_SOURCE = from("server/springboot/jackson/test");
  private static final String WIRE_JACKSON_CONFIG = "wire/jackson/infrastructure/primary";
  private static final PropertyKey SERVER_PORT = propertyKey("server.port");

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final String EXCEPTION_PRIMARY = "shared/error/infrastructure/primary";

  public SeedModule buildEmptyModule(SeedModuleProperties properties) {
    return moduleBuilder(properties).build();
  }

  public SeedModule buildNettyModule(SeedModuleProperties properties) {
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
