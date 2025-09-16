package com.seed4j.generator.server.springboot.webflux.web.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootWebfluxModuleFactoryTest {

  private static final SpringBootWebfluxModuleFactory factory = new SpringBootWebfluxModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("serverPort", 9000)
      .build();

    Seed4JModule module = factory.buildNettyModule(properties);

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
      .hasFile("src/main/java/com/seed4j/growth/wire/jackson/infrastructure/primary/JacksonConfiguration.java")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/wire/jackson/infrastructure/primary/JacksonConfigurationIT.java")
      .and()
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/shared/error/infrastructure/primary", "HeaderUtil.java", "FieldErrorDTO.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "HeaderUtilTest.java",
        "FieldErrorDTOTest.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/TestUtil.java");
  }
}
