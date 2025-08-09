package com.seed4j.generator.client.tools.cypress.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UnitTest
class CypressModuleFactoryTest {

  private static final CypressModuleFactory factory = new CypressModuleFactory();

  @Test
  void shouldBuildComponentTestsModule() {
    ModuleFile[] files = new ModuleFile[] { packageJsonFile(), eslintConfigFile() };
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildComponentTestsModule(properties);

    assertThatModuleWithFiles(module, files)
      .hasFile("package.json")
      .containing(nodeDependency("cypress"))
      .containing(nodeDependency("eslint-plugin-cypress"))
      .notContaining(nodeScript("e2e"))
      .notContaining(nodeScript("e2e:headless"))
      .containing(
        nodeScript(
          "test:component",
          "start-server-and-test dev http://localhost:9000 'cypress open --e2e --config-file src/test/webapp/component/cypress-config.ts'"
        )
      )
      .containing(
        nodeScript(
          "test:component:headless",
          "start-server-and-test dev http://localhost:9000 'cypress run --headless --config-file src/test/webapp/component/cypress-config.ts'"
        )
      )
      .and()
      .hasFile("src/test/webapp/component/cypress-config.ts")
      .containing("baseUrl: 'http://localhost:9000',")
      .containing("specPattern: 'src/test/webapp/component/**/*.(spec|cy).ts',")
      .and()
      .hasPrefixedFiles("src/test/webapp/component", "tsconfig.json")
      .hasFiles("src/test/webapp/component/home/Home.spec.ts")
      .hasPrefixedFiles("src/test/webapp/component/utils", "Interceptor.ts", "DataSelector.ts");
  }

  @Test
  @DisplayName("should build E2E tests module")
  void shouldBuildE2eTestsModule() {
    ModuleFile[] files = new ModuleFile[] { packageJsonFile(), eslintConfigFile() };
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildE2ETestsModule(properties);

    assertThatModuleWithFiles(module, files)
      .hasFile("package.json")
      .containing(nodeDependency("cypress"))
      .containing(nodeDependency("eslint-plugin-cypress"))
      .notContaining(nodeScript("test:component"))
      .notContaining(nodeScript("test:component:headless"))
      .containing(nodeScript("e2e", "cypress open --e2e --config-file src/test/webapp/e2e/cypress-config.ts"))
      .containing(nodeScript("e2e:headless", "cypress run --headless --config-file src/test/webapp/e2e/cypress-config.ts"))
      .and()
      .hasFile("src/test/webapp/e2e/cypress-config.ts")
      .containing("baseUrl: 'http://localhost:9000',")
      .containing("specPattern: 'src/test/webapp/e2e/**/*.(spec|cy).ts',")
      .and()
      .hasPrefixedFiles("src/test/webapp/e2e", "tsconfig.json")
      .hasFiles("src/test/webapp/e2e/home/Home.spec.ts")
      .hasPrefixedFiles("src/test/webapp/e2e/utils", "Interceptor.ts", "DataSelector.ts");
  }
}
