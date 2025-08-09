package com.seed4j.generator.client.react.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ReactJwtModuleFactoryTest {

  private static final String APP_TSX = "src/main/webapp/app/home/infrastructure/primary/HomePage.tsx";

  private static final ReactJwtModuleFactory factory = new ReactJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModule module = factory.buildModule(properties());

    JHipsterModuleAsserter asserter = assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      app(),
      appCss(),
      indexTsx(),
      indexCss(),
      viteReactConfigFile(),
      eslintConfigFile(),
      tsConfigFile(),
      vitestConfigFile(),
      tailwindConfigFile()
    );

    assertReactApp(asserter);
    asserter
      .hasFile("src/main/webapp/app/home/infrastructure/primary/HomePage.css")
      .containing(
        """
          -moz-osx-font-smoothing: grayscale;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        """
      )
      .and()
      .hasFile("src/main/webapp/app/index.tsx")
      .containing(
        """
          <React.StrictMode>
            <HeroUIProvider>
              <HomePage />
            </HeroUIProvider>
          </React.StrictMode>,
        """
      )
      .and()
      .hasFile("eslint.config.js")
      .matchingSavedSnapshot()
      .and()
      .hasFile("tsconfig.json")
      .matchingSavedSnapshot()
      .and()
      .hasFile("vitest.config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFile("vite.config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFile("tailwind.config.js")
      .matchingSavedSnapshot();
  }

  private ModuleFile app() {
    return file("src/test/resources/projects/react/HomePage.tsx", APP_TSX);
  }

  private ModuleFile appCss() {
    return file("src/test/resources/projects/react/HomePage.css", "src/main/webapp/app/home/infrastructure/primary/HomePage.css");
  }

  private ModuleFile indexTsx() {
    return file("src/test/resources/projects/react/index.tsx", "src/main/webapp/app/index.tsx");
  }

  private ModuleFile indexCss() {
    return file("src/test/resources/projects/react/index.css", "src/main/webapp/app/index.css");
  }

  private SeedModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }

  private ModuleFile tailwindConfigFile() {
    return file("src/main/resources/generator/client/react/security/jwt/tailwind.config.js", "tailwind.config.js");
  }

  private void assertReactApp(JHipsterModuleAsserter asserter) {
    asserter
      .hasFile("package.json")
      .containing(nodeDependency("autoprefixer"))
      .containing(nodeDependency("@tailwindcss/vite"))
      .containing(nodeDependency("tailwindcss"))
      .containing(nodeDependency("react-hook-form"))
      .containing(nodeDependency("axios"))
      .containing(nodeDependency("@heroui/react"))
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
        "src/test/webapp/unit",
        "login/services/login.spec.ts",
        "login/primary/loginForm/index.spec.tsx",
        "login/primary/loginModal/index.spec.tsx",
        "common/services/storage.spec.ts"
      )
      .hasFile("src/main/webapp/app/home/infrastructure/primary/HomePage.tsx")
      .containing("import LoginForm from '@/login/primary/loginForm';")
      .containing("<LoginForm />");
  }
}
