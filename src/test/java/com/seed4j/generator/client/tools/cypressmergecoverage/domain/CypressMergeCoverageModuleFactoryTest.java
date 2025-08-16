package com.seed4j.generator.client.tools.cypressmergecoverage.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.cypressTestConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.eslintConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.packageJsonFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.tsConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.viteConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.vitestConfigFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CypressMergeCoverageModuleFactoryTest {

  @InjectMocks
  private CypressMergeCoverageModuleFactory factory;

  @Test
  void shouldBuildCypressMergeCoverageModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("growth")
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildCypressMergeCoverage(properties);

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      tsConfigFile(),
      vitestConfigFile(),
      viteConfigFile(),
      eslintConfigFile(),
      pomFile(),
      cypressTestConfigFile()
    )
      .hasFile("package.json")
      .containing(nodeDependency("@cypress/code-coverage"))
      .containing(nodeDependency("cpy-cli"))
      .containing(nodeDependency("rimraf"))
      .containing(nodeDependency("vite-plugin-istanbul"))
      .containing(nodeDependency("nyc"))
      .containing(nodeScript("test:coverage:check"))
      .containing(nodeScript("test:coverage:clean"))
      .containing(nodeScript("test:coverage:copy:unit"))
      .containing(nodeScript("test:coverage:copy:component"))
      .containing(nodeScript("test:coverage:merge"))
      .containing(nodeScript("test:coverage:report"))
      .and()
      .hasFile("vite.config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFile("vitest.config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFile(".nycrc.json")
      .and()
      .hasFile("src/test/webapp/component/cypress-config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFile("src/test/webapp/component/.nycrc.json")
      .and()
      .hasFile("src/test/webapp/component/support/component-tests.ts");
  }
}
