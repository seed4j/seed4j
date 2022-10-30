package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootMvcsModulesFactoryTest {

  private static final SpringBootMvcsModulesFactory factory = new SpringBootMvcsModulesFactory();

  @Test
  void shouldBuildTomcatMvcModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
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
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
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
    return assertThatModuleWithFiles(module, pomFile(), lockbackFile(), testLockbackFile(), readmeFile())
      .hasFile("README.md")
      .containing("- [Local server](http://localhost:9000)")
      .and()
      .hasFiles("documentation/cors-configuration.md")
      .hasFile("src/main/resources/config/application.properties")
      .containing("server.port=9000")
      .and()
      .hasFile("src/main/resources/public/error/404.html")
      .and()
      .hasFiles("src/main/java/com/jhipster/test/technical/infrastructure/primary/exception/ErrorDTO.java")
      .hasFiles("src/main/java/com/jhipster/test/technical/infrastructure/primary/exception/GlobalExceptionHandler.java")
      .hasFiles("src/test/java/com/jhipster/test/technical/infrastructure/primary/exception/GlobalExceptionHandlerIT.java")
      .hasFiles("src/test/java/com/jhipster/test/technical/infrastructure/primary/exception/GlobalExceptionHandlerTestController.java")
      .hasFile("src/test/resources/config/application.properties")
      .containing("server.port=0")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/security/infrastructure/primary",
        "CorsFilterConfiguration.java",
        "CorsProperties.java"
      )
      .hasPrefixedFiles("src/test/java/com/jhipster/test", "BeanValidationAssertions.java", "BeanValidationTest.java")
      .hasFiles("src/test/java/com/jhipster/test/security/infrastructure/primary/CorsFilterConfigurationIT.java")
      .hasFiles("src/test/java/com/jhipster/test/JsonHelper.java")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
        """
      )
      .and();
  }
}
