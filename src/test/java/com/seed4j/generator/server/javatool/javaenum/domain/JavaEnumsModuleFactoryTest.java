package com.seed4j.generator.server.javatool.javaenum.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaEnumsModuleFactoryTest {

  private static final JavaEnumsModuleFactory factory = new JavaEnumsModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/enumeration/package-info.java")
      .hasPrefixedFiles("src/main/java/tech/jhipster/jhlitest/shared/enumeration/domain/", "Enums.java", "UnmappableEnumException.java")
      .hasFiles("src/test/java/tech/jhipster/jhlitest/shared/enumeration/domain/EnumsTest.java");
  }
}
