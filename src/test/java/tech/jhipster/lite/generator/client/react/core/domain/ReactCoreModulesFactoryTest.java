package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class ReactCoreModulesFactoryTest {

  private static final ReactCoreModulesFactory factory = new ReactCoreModulesFactory();

  @Test
  void shouldBuildModuleWithStyle() {
    JHipsterModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build()
    );

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFile("package.json")
      .containing("\"type\": \"module\"")
      .containing(nodeDependency("@testing-library/dom"))
      .containing(nodeDependency("@testing-library/react"))
      .containing(nodeDependency("@types/node"))
      .containing(nodeDependency("@types/react"))
      .containing(nodeDependency("@types/react-dom"))
      .containing(nodeDependency("@tsconfig/vite-react"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@vitejs/plugin-react"))
      .containing(nodeDependency("@vitest/coverage-istanbul"))
      .containing(nodeDependency("typescript-eslint"))
      .containing(nodeDependency("globals"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("eslint-plugin-react"))
      .containing(nodeDependency("jsdom"))
      .containing(nodeDependency("typescript"))
      .containing(nodeDependency("ts-node"))
      .containing(nodeDependency("vite"))
      .containing(nodeDependency("vite-tsconfig-paths"))
      .containing(nodeDependency("vitest"))
      .containing(nodeDependency("vitest-sonar-reporter"))
      .containing(nodeDependency("react"))
      .containing(nodeDependency("npm-run-all2"))
      .containing(nodeDependency("react-dom"))
      .containing(nodeScript("dev", "npm-run-all dev:*"))
      .containing(nodeScript("dev:vite", "vite"))
      .containing(nodeScript("build", "npm-run-all build:*"))
      .containing(nodeScript("build:tsc", "tsc"))
      .containing(nodeScript("build:vite", "vite build --emptyOutDir"))
      .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
      .containing(nodeScript("watch:tsc", "tsc --noEmit --watch"))
      .containing(nodeScript("preview", "vite preview"))
      .containing(nodeScript("start", "vite"))
      .containing(nodeScript("lint", "eslint ."))
      .containing(nodeScript("test", "npm run watch:test"))
      .containing(nodeScript("test:coverage", "vitest run --coverage"))
      .containing(nodeScript("watch:test", "vitest --"))
      .and()
      .hasFile(".lintstagedrc.cjs")
      .containing(
        """
        module.exports = {
          '{src/**/,}*.{ts,tsx}': ['eslint --fix', 'prettier --write'],
          '*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
        };
        """
      )
      .and()
      .hasFiles("tsconfig.json", "vite.config.ts", "vitest.config.ts", "eslint.config.js")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "index.css", "index.tsx", "vite-env.d.ts")
      .hasFiles("src/test/webapp/unit/common/primary/app/App.spec.tsx")
      .hasFile("src/main/webapp/app/common/primary/app/App.tsx")
      .containing("import './App.css';")
      .and()
      .hasFiles("src/main/webapp/app/common/primary/app/App.css")
      .hasPrefixedFiles("src/main/webapp/assets", "ReactLogo.png", "JHipster-Lite-neon-blue.png");
  }

  @Test
  void shouldViteConfigBeUpdatedWhenServerPortPropertyNotDefault() {
    JHipsterModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").put("serverPort", 8081).build()
    );

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("vite.config.ts")
      .containing("localhost:8081")
      .notContaining("localhost:8080");
  }

  @Test
  void shouldViteConfigBeDefaultWhenServerPortPropertyMissing() {
    JHipsterModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build()
    );

    assertThatModuleWithFiles(module, packageJsonFile()).hasFile("vite.config.ts").containing("localhost:8080");
  }

  private String nodeScript(String key, String value) {
    return "\"" + key + "\": \"" + value + "\"";
  }
}
