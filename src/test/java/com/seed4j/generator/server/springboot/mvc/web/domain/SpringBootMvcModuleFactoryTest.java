package com.seed4j.generator.server.springboot.mvc.web.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.Seed4JModuleAsserter;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.logbackFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.readmeFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.testLogbackFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootMvcModuleFactoryTest {

  private static final SpringBootMvcModuleFactory factory = new SpringBootMvcModuleFactory();

  @Test
  void shouldBuildTomcatMvcModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("serverPort", 9000)
      .build();

    Seed4JModule module = factory.buildTomcatModule(properties);

    assertMvcModule(module)
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("  <logger name=\"org.springframework.web\" level=\"ERROR\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("  <logger name=\"org.springframework.web\" level=\"ERROR\" />")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-webmvc</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-webmvc-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.reflections</groupId>
              <artifactId>reflections</artifactId>
              <version>${reflections.version}</version>
              <scope>test</scope>
            </dependency>
        """
      );
  }

  private Seed4JModuleAsserter assertMvcModule(Seed4JModule module) {
    return assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFile("README.md")
      .containing("- [Local server](http://localhost:9000)")
      .and()
      .hasFiles("documentation/cors-configuration.md")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        server:
          port: 9000
        spring:
          jackson:
            default-property-inclusion: non_absent
        """
      )
      .and()
      .hasFile("src/main/resources/public/error/404.html")
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        server:
          port: 0
        """
      )
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/wire/security/infrastructure/primary",
        "CorsFilterConfiguration.java",
        "CorsProperties.java"
      )
      .hasFiles("src/main/java/com/seed4j/growth/wire/security/package-info.java")
      .hasPrefixedFiles("src/test/java/com/seed4j/growth", "BeanValidationAssertions.java", "BeanValidationTest.java")
      .hasFiles("src/test/java/com/seed4j/growth/wire/security/infrastructure/primary/CorsFilterConfigurationIT.java")
      .hasFiles("src/test/java/com/seed4j/growth/JsonHelper.java")
      .hasFiles("src/main/java/com/seed4j/growth/shared/error/infrastructure/primary/BeanValidationErrorsHandler.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "BeanValidationErrorsHandlerTest.java",
        "BeanValidationErrorsHandlerIT.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error_generator/infrastructure/primary",
        "BeanValidationErrorsResource.java",
        "RestMandatoryParameter.java"
      )
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/java/com/seed4j/growth/wire/jackson/infrastructure/primary/JacksonConfiguration.java")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/wire/jackson/infrastructure/primary/JacksonConfigurationIT.java")
      .and();
  }
}
