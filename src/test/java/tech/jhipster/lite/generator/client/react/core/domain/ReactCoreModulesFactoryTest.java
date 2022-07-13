package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.JHipsterModuleAsserter;

@UnitTest
class ReactCoreModulesFactoryTest {

  private static final ReactCoreModulesFactory factory = new ReactCoreModulesFactory();

  @Test
  void shouldBuildModuleWithoutStyle() {
    JHipsterModule module = factory.buildModuleWithoutStyle(properties());

    assertCommonReactModule(module).hasFile("src/main/webapp/app/common/primary/app/App.tsx").notContaining("import './App.css';");
  }

  @Test
  void shouldBuildModuleWithStyle() {
    JHipsterModule module = factory.buildModuleWithStyle(properties());

    assertCommonReactModule(module)
      .hasFile("src/main/webapp/app/common/primary/app/App.tsx")
      .containing("import './App.css';")
      .and()
      .hasFiles("src/main/webapp/app/common/primary/app/App.css")
      .hasPrefixedFiles("src/main/webapp/content/images", "ReactLogo.png", "JHipster-Lite-neon-blue.png");
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build();
  }

  private JHipsterModuleAsserter assertCommonReactModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing(nodeDependency("@testing-library/jest-dom"))
      .containing(nodeDependency("@testing-library/react"))
      .containing(nodeDependency("@testing-library/user-event"))
      .containing(nodeDependency("@types/jest"))
      .containing(nodeDependency("@types/node"))
      .containing(nodeDependency("@types/react"))
      .containing(nodeDependency("@types/react-dom"))
      .containing(nodeDependency("@types/ws"))
      .containing(nodeDependency("@vitejs/plugin-react"))
      .containing(nodeDependency("jest"))
      .containing(nodeDependency("jest-sonar-reporter"))
      .containing(nodeDependency("jest-css-modules"))
      .containing(nodeDependency("jest-environment-jsdom"))
      .containing(nodeDependency("typescript"))
      .containing(nodeDependency("ts-jest"))
      .containing(nodeDependency("ts-node"))
      .containing(nodeDependency("vite"))
      .containing(nodeDependency("react"))
      .containing(nodeDependency("react-dom"))
      .containing(nodeScript("dev", "vite"))
      .containing(nodeScript("build", "tsc && vite build --emptyOutDir"))
      .containing(nodeScript("preview", "vite preview"))
      .containing(nodeScript("start", "vite"))
      .containing(nodeScript("test", "jest"))
      .containing(nodeScript("test:watch", "jest --watch"))
      .containing(
        "  \"jestSonar\": {\n    \"reportPath\": \"target/test-results/jest\",\n    \"reportFile\": \"TESTS-results-sonar.xml\"\n  },"
      )
      .and()
      .hasFiles("tsconfig.json", "vite.config.ts", "jest.config.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "index.css", "index.tsx", "vite-env.d.ts")
      .hasFiles("src/test/javascript/spec/common/primary/app/App.spec.tsx")
      .hasFiles("src/main/webapp/config/setupTests.ts");
  }

  private static String nodeDependency(String dependency) {
    return "\"" + dependency + "\": \"";
  }

  private String nodeScript(String key, String value) {
    return "\"" + key + "\": \"" + value + "\"";
  }
}
