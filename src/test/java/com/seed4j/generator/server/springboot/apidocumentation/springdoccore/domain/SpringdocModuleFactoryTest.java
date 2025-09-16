package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocModuleFactoryTest {

  private static final SpringdocModuleFactory springdocModuleFactory = new SpringdocModuleFactory();

  @Test
  void shouldBuildModuleForMvc() {
    Seed4JModule module = springdocModuleFactory.buildModuleForMvc(properties());

    assertThatSpringdocModule(module)
      .hasFile("src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>")
      .containing("<artifactId>springdoc-openapi-starter-webmvc-api</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>");
  }

  @Test
  void shouldBuildModuleForWebflux() {
    Seed4JModule module = springdocModuleFactory.buildModuleForWebflux(properties());

    assertThatSpringdocModule(module)
      .hasFile("src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>")
      .containing("<artifactId>springdoc-openapi-starter-webflux-api</artifactId>");
  }

  private Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();
  }

  private static Seed4JModuleAsserter assertThatSpringdocModule(Seed4JModule module) {
    return assertThatModuleWithFiles(module, pomFile(), readmeFile(), logbackFile(), testLogbackFile())
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        springdoc:
          enable-native-support: true
          swagger-ui:
            operationsSorter: alpha
            tagsSorter: alpha
            tryItOutEnabled: true
        """
      )
      .and()
      .hasFile("README.md")
      .containing("- [Local API doc](http://localhost:8080/swagger-ui.html)")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"io.swagger.v3.core.converter.ModelConverterContextImpl\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"io.swagger.v3.core.converter.ModelConverterContextImpl\" level=\"WARN\" />")
      .and();
  }
}
