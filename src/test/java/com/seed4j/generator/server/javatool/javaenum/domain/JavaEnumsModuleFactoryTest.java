package com.seed4j.generator.server.javatool.javaenum.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaEnumsModuleFactoryTest {

  private static final JavaEnumsModuleFactory factory = new JavaEnumsModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("src/main/java/com/seed4j/growth/shared/enumeration/package-info.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/shared/enumeration/domain/", "Enums.java", "UnmappableEnumException.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/enumeration/domain/EnumsTest.java");
  }
}
