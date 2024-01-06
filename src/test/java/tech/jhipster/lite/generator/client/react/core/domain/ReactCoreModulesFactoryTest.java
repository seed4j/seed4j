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
      .containing(nodeDependency("@testing-library/react"))
      .containing(nodeDependency("@testing-library/user-event"))
      .containing(nodeDependency("@types/node"))
      .containing(nodeDependency("@types/react"))
      .containing(nodeDependency("@types/react-dom"))
      .containing(nodeDependency("@types/ws"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@vitejs/plugin-react"))
      .containing(nodeDependency("@vitest/coverage-istanbul"))
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
      .containing(nodeDependency("react-dom"))
      .containing(nodeScript("dev", "vite"))
      .containing(nodeScript("build", "tsc && vite build --emptyOutDir"))
      .containing(nodeScript("preview", "vite preview"))
      .containing(nodeScript("start", "vite"))
      .containing(nodeScript("lint", "eslint --ext .js,.ts,.tsx src/main/webapp/app/**/*"))
      .containing(nodeScript("test", "vitest run --coverage"))
      .containing(nodeScript("test:watch", "vitest --"))
      .and()
      .hasFile(".lintstagedrc.js")
      .containing(
        """
        module.exports = {
          '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],
          '{src/**/,}*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
        };
        """
      )
      .and()
      .hasFiles("tsconfig.json", "vite.config.mts", "vitest.config.mts", ".eslintignore", ".eslintrc.cjs")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "index.css", "index.tsx", "vite-env.d.ts")
      .hasFiles("src/test/javascript/spec/common/primary/app/App.spec.tsx")
      .hasFile("src/main/webapp/app/common/primary/app/App.tsx")
      .containing("import './App.css';")
      .and()
      .hasFiles("src/main/webapp/app/common/primary/app/App.css")
      .hasPrefixedFiles("src/main/webapp/assets", "ReactLogo.png", "JHipster-Lite-neon-blue.png");
  }

  private String nodeScript(String key, String value) {
    return "\"" + key + "\": \"" + value + "\"";
  }
}
