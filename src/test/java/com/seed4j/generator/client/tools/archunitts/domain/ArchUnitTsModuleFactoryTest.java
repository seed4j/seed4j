package com.seed4j.generator.client.tools.archunitts.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ArchUnitTsModuleFactoryTest {

  private static final ArchUnitTsModuleFactory factory = new ArchUnitTsModuleFactory();

  @Test
  void shouldBuildArchUnitTsModule() {
    String folder = TestFileUtils.tmpDirForTest();
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(folder).build();

    SeedModule module = factory.buildModule(properties);

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
