package com.seed4j.generator.client.tools.archunitts.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ArchUnitTsModuleFactoryTest {

  private static final ArchUnitTsModuleFactory factory = new ArchUnitTsModuleFactory();

  @Test
  void shouldBuildArchUnitTsModule() {
    String folder = TestFileUtils.tmpDirForTest();
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(folder).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing(nodeDependency("arch-unit-ts"))
      .and()
      .hasFiles("arch-unit-ts.json")
      .hasPrefixedFiles("src/main/webapp/app", "SharedKernel.ts", "BusinessContext.ts")
      .hasPrefixedFiles("src/main/webapp/app/home", "package-info.ts")
      .hasPrefixedFiles("src/main/webapp/app/shared", "package-info.ts")
      .hasPrefixedFiles("src/test/webapp/unit", "HexagonalArchTest.spec.ts");
  }
}
