package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.nodeDependency;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.nodeScript;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.packageJsonFile;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CypressModuleFactoryTest {

  private static final CypressModuleFactory factory = new CypressModuleFactory();

  @Nested
  class ComponentTests {

    @Test
    void shouldBuildComponentTestsModule() {
      ModuleFile[] files = new ModuleFile[] { packageJsonFile() };
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildComponentTestsModule(properties);

      assertThatModuleWithFiles(module, files)
        .hasFile("package.json")
        .containing(nodeDependency("cypress"))
        .containing(nodeDependency("eslint-plugin-cypress"))
        .notContaining(nodeScript("e2e"))
        .notContaining(nodeScript("e2e:headless"))
        .containing(
          nodeScript(
            "test:component",
            "start-server-and-test start http://localhost:9000 'cypress open --e2e --config-file src/test/webapp/component/cypress-config.ts'"
          )
        )
        .containing(
          nodeScript(
            "test:component:headless",
            "start-server-and-test tikui:serve-build http://localhost:9005 start http://localhost:9000 'cypress run --headless --config-file src/test/webapp/component/cypress-config.ts'"
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
  }

  @Nested
  class E2ETests {

    @Test
    void shouldBuildE2eTestsModule() {
      ModuleFile[] files = new ModuleFile[] { packageJsonFile() };
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildE2ETestsModule(properties);

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
}
