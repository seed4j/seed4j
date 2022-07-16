package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleAsserter;

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
      .createFile("src/main/resources/logback-spring.xml")
      .containing("  <logger name=\"org.springframework.web\" level=\"ERROR\" />")
      .and()
      .createFile("src/test/resources/logback.xml")
      .containing("  <logger name=\"org.springframework.web\" level=\"ERROR\" />")
      .and()
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
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

    JHipsterModule module = factory.buildUntertowModule(properties);

    assertMvcModule(module)
      .createFile("src/main/resources/logback-spring.xml")
      .containing("  <logger name=\"io.undertow\" level=\"WARN\" />")
      .and()
      .createFile("src/test/resources/logback.xml")
      .containing("  <logger name=\"io.undertow\" level=\"WARN\" />")
      .and()
      .createFile("pom.xml")
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

  private ModuleAsserter assertMvcModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile(), lockbackFile(), testLockbackFile())
      .createFiles("documentation/cors-configuration.md")
      .createFile("src/main/resources/config/application.properties")
      .containing("server.port=9000")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("server.port=0")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/primary/cors",
        "CorsFilterConfiguration.java",
        "CorsProperties.java"
      )
      .createFiles("src/test/java/com/jhipster/test/technical/infrastructure/primary/cors/CorsFilterConfigurationIT.java")
      .createFiles("src/test/java/com/jhipster/test/JsonHelper.java")
      .createFile("pom.xml")
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
