package com.seed4j.generator.typescript.optional.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class OptionalTypescriptModuleFactoryTest {

  private static final OptionalTypescriptModuleFactory factory = new OptionalTypescriptModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module)
      .hasFile("src/main/webapp/app/common/domain/Optional.ts")
      .and()
      .hasFile("src/test/webapp/unit/common/domain/Optional.spec.ts");
  }
}
