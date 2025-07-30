package com.seed4j.generator.server.javatool.archunit.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ArchUnitModuleFactoryTest {

  private static final ArchUnitModuleFactory factory = new ArchUnitModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), testLogbackFile())
      .hasFiles(
        "src/test/resources/archunit.properties",
        "src/test/java/com/seed4j/growth/AnnotationArchTest.java",
        "src/test/java/com/seed4j/growth/HexagonalArchTest.java",
        "src/test/java/com/seed4j/growth/EqualsHashcodeArchTest.java"
      )
      .hasFile("pom.xml")
      .containing("<artifactId>archunit-junit5-api</artifactId>")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"com.tngtech.archunit\" level=\"WARN\" />");
  }
}
