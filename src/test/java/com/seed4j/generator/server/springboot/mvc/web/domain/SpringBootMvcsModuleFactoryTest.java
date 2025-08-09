package com.seed4j.generator.server.springboot.mvc.web.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootMvcsModuleFactoryTest {

  private static final SpringBootMvcModuleFactory factory = new SpringBootMvcModuleFactory();

  @Test
  void shouldBuildTomcatMvcModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildTomcatModule(properties);

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
              <artifactId>spring-boot-starter-web</artifactId>
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

  @Test
  void shouldBuildUndertowModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildUndertowModule(properties);

    assertMvcModule(module)
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("  <logger name=\"io.undertow\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("  <logger name=\"io.undertow\" level=\"WARN\" />")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
              <exclusions>
                <exclusion>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
              </exclusions>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-undertow</artifactId>
            </dependency>
        """
      );
  }

  private JHipsterModuleAsserter assertMvcModule(JHipsterModule module) {
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
