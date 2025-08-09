package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocModuleFactoryTest {

  private static final SpringdocModuleFactory springdocModuleFactory = new SpringdocModuleFactory();

  @Test
  void shouldBuildModuleForMvc() {
    SeedModule module = springdocModuleFactory.buildModuleForMvc(properties());

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
    SeedModule module = springdocModuleFactory.buildModuleForWebflux(properties());

    assertThatSpringdocModule(module)
      .hasFile("src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>")
      .containing("<artifactId>springdoc-openapi-starter-webflux-api</artifactId>");
  }

  private SeedModuleProperties properties() {
    return SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();
  }

  private static SeedModuleAsserter assertThatSpringdocModule(SeedModule module) {
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
