package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class ReactJwtModuleFactoryTest {

  private static final String APP_TSX = "src/main/webapp/app/common/primary/app/App.tsx";

  private static final ReactJwtModuleFactory factory = new ReactJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModule module = factory.buildModule(properties());

    JHipsterModuleAsserter asserter = assertThatModuleWithFiles(module, packageJsonFile(), app(), appCss(), indexTsx(), indexCss());

    assertReactApp(asserter);
    asserter
      .hasFile("src/main/webapp/app/common/primary/app/App.css")
      .containing(
        """
          -moz-osx-font-smoothing: grayscale;
          display: flex;
          flex-direction: column;
          justify-content:center;
          align-items: center;
        """
      );
  }

  private ModuleFile app() {
    return file("src/test/resources/projects/react-app/App.tsx", APP_TSX);
  }

  private ModuleFile appCss() {
    return file("src/test/resources/projects/react-app/App.css", "src/main/webapp/app/common/primary/app/App.css");
  }

  private ModuleFile indexTsx() {
    return file("src/test/resources/projects/react-app/index.tsx", "src/main/webapp/app/index.tsx");
  }

  private ModuleFile indexCss() {
    return file("src/test/resources/projects/react-app/index.css", "src/main/webapp/app/index.css");
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }

  private void assertReactApp(JHipsterModuleAsserter asserter) {
    asserter
      .hasFile("package.json")
      .containing(nodeDependency("autoprefixer"))
      .containing(nodeDependency("postcss"))
      .containing(nodeDependency("tailwindcss"))
      .containing(nodeDependency("framer-motion"))
      .containing(nodeDependency("react-hook-form"))
      .containing(nodeDependency("axios"))
      .containing(nodeDependency("@nextui-org/react"))
      .containing(nodeDependency("sass"))
      .and()
      .hasPrefixedFiles(
        "src/main/webapp",
        "app/common/services/storage.ts",
        "app/login/primary/loginForm/index.tsx",
        "app/login/primary/loginModal/index.tsx",
        "app/login/services/login.ts"
      )
      .hasPrefixedFiles(
        "src/main/webapp/app/login/primary/loginModal",
        "EyeSlashFilledIcon.tsx",
        "EyeFilledIcon.tsx",
        "index.tsx",
        "interface.d.ts",
        "LoginModal.scss"
      )
      .hasPrefixedFiles(
        "src/test/javascript/spec",
        "login/services/login.test.ts",
        "login/primary/loginForm/index.test.tsx",
        "login/primary/loginModal/index.test.tsx",
        "common/services/storage.test.ts"
      )
      .hasFile("src/main/webapp/app/common/primary/app/App.tsx")
      .containing("import LoginForm from '@/login/primary/loginForm';")
      .containing("<LoginForm />");
  }
}
