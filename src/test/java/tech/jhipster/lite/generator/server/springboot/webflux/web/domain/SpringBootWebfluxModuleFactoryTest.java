package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootWebfluxModuleFactoryTest {

  private static final SpringBootWebfluxModuleFactory factory = new SpringBootWebfluxModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildNettyModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-webflux</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.projectreactor</groupId>
              <artifactId>reactor-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
        """
      )
      .and()
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
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        server:
          port: 0
        """
      )
      .and()
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/jackson/infrastructure/primary/JacksonConfiguration.java")
      .and()
      .hasFile("src/test/java/tech/jhipster/jhlitest/wire/jackson/infrastructure/primary/JacksonConfigurationIT.java")
      .and()
      .hasPrefixedFiles("src/main/java/tech/jhipster/jhlitest/shared/error/infrastructure/primary", "HeaderUtil.java", "FieldErrorDTO.java")
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/error/infrastructure/primary",
        "HeaderUtilTest.java",
        "FieldErrorDTOTest.java"
      )
      .hasFiles("src/test/java/tech/jhipster/jhlitest/TestUtil.java");
  }
}
