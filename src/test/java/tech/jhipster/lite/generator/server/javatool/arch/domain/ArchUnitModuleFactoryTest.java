package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class ArchUnitModuleFactoryTest {

  private static final ArchUnitModuleFactory factory = new ArchUnitModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), testLockbackFile())
      .hasFiles("src/test/resources/archunit.properties")
      .hasFile("src/test/java/com/jhipster/test/HexagonalArchTest.java")
      .containing("\"src\", \"main\", \"java\", \"com\", \"jhipster\", \"test\"")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>archunit-junit5-api</artifactId>")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"com.tngtech.archunit\" level=\"WARN\" />");
  }
}
