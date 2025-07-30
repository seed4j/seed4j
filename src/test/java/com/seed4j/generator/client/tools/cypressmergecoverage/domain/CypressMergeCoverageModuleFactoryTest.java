package com.seed4j.generator.client.tools.cypressmergecoverage.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.cypressTestConfigFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.eslintConfigFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.packageJsonFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.tsConfigFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.viteConfigFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.vitestConfigFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildCypressMergeCoverage(properties);

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
