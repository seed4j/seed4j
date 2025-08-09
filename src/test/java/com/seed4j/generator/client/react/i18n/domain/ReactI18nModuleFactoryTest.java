package com.seed4j.generator.client.react.i18n.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import org.junit.jupiter.api.Test;

@UnitTest
class ReactI18nModuleFactoryTest {

  private static final String HOME_PAGE_TSX = "src/main/webapp/app/home/infrastructure/primary/HomePage.tsx";

  private final ReactI18nModuleFactory factory = new ReactI18nModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build()
    );

    JHipsterModuleAsserter asserter = assertThatModuleWithFiles(module, packageJsonFile(), app(), appTest(), index(), vitest());

    asserter
      .hasFile("package.json")
      .containing(nodeDependency("i18next"))
      .containing(nodeDependency("i18next-browser-languagedetector"))
      .containing(nodeDependency("i18next-http-backend"))
      .containing(nodeDependency("react-i18next"))
      .and()
      .hasFiles("src/test/webapp/unit/i18n.spec.ts", "src/main/webapp/app/Translations.ts")
      .hasFile("src/main/webapp/app/i18n.ts")
      .containing("import { initReactI18next } from 'react-i18next';")
      .containing("use(initReactI18next)")
      .and()
      .hasFile("src/main/webapp/app/index.tsx")
      .containing("import './i18n'")
      .and()
      .hasFile(HOME_PAGE_TSX)
      .matchingSavedSnapshot()
      .and()
      .hasFile("src/main/webapp/app/home/HomeTranslations.ts")
      .and()
      .hasPrefixedFiles("src/main/webapp/app/home/locales", "en.ts", "fr.ts")
      .hasFile("src/test/webapp/unit/home/infrastructure/primary/HomePage.spec.tsx")
      .containing("describe('Home I18next', () => {");
  }

  private ModuleFile app() {
    return file("src/test/resources/projects/react/HomePage.tsx", HOME_PAGE_TSX);
  }

  private ModuleFile appTest() {
    return file(
      "src/test/resources/projects/react/HomePage.spec.tsx",
      "src/test/webapp/unit/home/infrastructure/primary/HomePage.spec.tsx"
    );
  }

  private ModuleFile index() {
    return file("src/test/resources/projects/react/index.tsx", "src/main/webapp/app/index.tsx");
  }

  private ModuleFile vitest() {
    return file("src/test/resources/projects/react/vitest.config.ts.mustache", "./vitest.config.ts");
  }
}
