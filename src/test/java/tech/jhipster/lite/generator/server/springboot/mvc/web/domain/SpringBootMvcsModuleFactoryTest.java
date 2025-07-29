package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootMvcsModuleFactoryTest {

  private static final SpringBootMvcModuleFactory factory = new SpringBootMvcModuleFactory();

  @Test
  void shouldBuildTomcatMvcModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
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
          forward-headers-strategy: FRAMEWORK
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
        "src/main/java/tech/jhipster/jhlitest/wire/security/infrastructure/primary",
        "CorsFilterConfiguration.java",
        "CorsProperties.java"
      )
      .hasFiles("src/main/java/tech/jhipster/jhlitest/wire/security/package-info.java")
      .hasPrefixedFiles("src/test/java/tech/jhipster/jhlitest", "BeanValidationAssertions.java", "BeanValidationTest.java")
      .hasFiles("src/test/java/tech/jhipster/jhlitest/wire/security/infrastructure/primary/CorsFilterConfigurationIT.java")
      .hasFiles("src/test/java/tech/jhipster/jhlitest/JsonHelper.java")
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/error/infrastructure/primary/BeanValidationErrorsHandler.java")
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/error/infrastructure/primary",
        "BeanValidationErrorsHandlerTest.java",
        "BeanValidationErrorsHandlerIT.java"
      )
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/error_generator/infrastructure/primary",
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
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/jackson/infrastructure/primary/JacksonConfiguration.java")
      .and()
      .hasFile("src/test/java/tech/jhipster/jhlitest/wire/jackson/infrastructure/primary/JacksonConfigurationIT.java")
      .and();
  }
}
