package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

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
    void shouldBuildComponentTestsModuleOnProjectWithoutTsConfig() {
      assertCypressComponentTestsModule(packageJsonFile());
    }

    @Test
    void shouldBuildComponentTestsModuleOnProjectWithEmptyTsConfig() {
      assertCypressComponentTestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/empty-ts-config/tsconfig.json", "tsconfig.json")
      ).hasFile("tsconfig.json");
    }

    @Test
    void shouldBuildComponentTestsModuleOnProjectWithTsConfigWithEmptyExclusions() {
      assertCypressComponentTestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-empty-exclusions/tsconfig.json", "tsconfig.json")
      )
        .hasFile("tsconfig.json")
        .containing("\"exclude\": []");
    }

    @Test
    void shouldBuildComponentTestsModuleOnProjectWithTsConfigWithExistingExclusions() {
      assertCypressComponentTestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-exclusions/tsconfig.json", "tsconfig.json")
      )
        .hasFile("tsconfig.json")
        .containing("  \"exclude\": [\"src/test/webapp/integration/**/*spec.ts\", \"node_modules\"]");
    }

    @Test
    void shouldNotDuplicateCypressExclusion() {
      assertCypressComponentTestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-cypress-component-exclusions/tsconfig.json", "tsconfig.json")
      )
        .hasFile("tsconfig.json")
        .containing("  \"exclude\": [\"src/test/webapp/component/**/*spec.ts\", \"node_modules\", \"src/test/webapp/component/**/*.ts\"]");
    }

    private static JHipsterModuleAsserter assertCypressComponentTestsModule(ModuleFile... files) {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildComponentTestsModule(properties);

      return assertThatModuleWithFiles(module, files)
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
            "start-server-and-test start http://localhost:9000 'cypress run --headless --config-file src/test/webapp/component/cypress-config.ts'"
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
    void shouldBuildE2eTestsModuleOnProjectWithoutTsConfig() {
      assertCypressE2ETestsModule(packageJsonFile());
    }

    @Test
    void shouldBuildE2eTestsModuleOnProjectWithEmptyTsConfig() {
      assertCypressE2ETestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/empty-ts-config/tsconfig.json", "tsconfig.json")
      ).hasFile("tsconfig.json");
    }

    @Test
    void shouldBuildE2eTestsModuleOnProjectWithTsConfigWithEmptyExclusions() {
      assertCypressE2ETestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-empty-exclusions/tsconfig.json", "tsconfig.json")
      ).hasFile("tsconfig.json");
    }

    @Test
    void shouldBuildE2eTestsModuleOnProjectWithTsConfigWithExistingExclusions() {
      assertCypressE2ETestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-exclusions/tsconfig.json", "tsconfig.json")
      )
        .hasFile("tsconfig.json")
        .containing("  \"exclude\": [\"src/test/webapp/integration/**/*spec.ts\", \"node_modules\"]");
    }

    @Test
    void shouldNotDuplicateCypressExclusion() {
      assertCypressE2ETestsModule(
        packageJsonFile(),
        file("src/test/resources/projects/ts-config-with-cypress-e2e-exclusions/tsconfig.json", "tsconfig.json")
      )
        .hasFile("tsconfig.json")
        .containing("  \"exclude\": [\"src/test/webapp/e2e/**/*spec.ts\", \"node_modules\"]");
    }

    private static JHipsterModuleAsserter assertCypressE2ETestsModule(ModuleFile... files) {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildE2ETestsModule(properties);

      return assertThatModuleWithFiles(module, files)
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
