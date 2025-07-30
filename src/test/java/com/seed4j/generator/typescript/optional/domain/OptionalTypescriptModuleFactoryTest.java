package com.seed4j.generator.typescript.optional.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class OptionalTypescriptModuleFactoryTest {

  private static final OptionalTypescriptModuleFactory factory = new OptionalTypescriptModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module)
      .hasFile("src/main/webapp/app/common/domain/Optional.ts")
      .and()
      .hasFile("src/test/webapp/unit/common/domain/Optional.spec.ts");
  }
}
