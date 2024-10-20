package tech.jhipster.lite.generator.client.react.i18n.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class ReactI18nModuleFactoryTest {

  private static final String HOME_PAGE_TSX = "src/main/webapp/app/home/infrastructure/primary/HomePage.tsx";

  private final ReactI18nModuleFactory factory = new ReactI18nModuleFactory();

  @Test
  void shouldBuildI18nModule() {
    JHipsterModule module = factory.buildModule(
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
      .hasFiles("src/main/webapp/app/i18n.ts")
      .hasFile("src/main/webapp/app/index.tsx")
      .containing("import './i18n'")
      .and()
      .hasFile(HOME_PAGE_TSX)
      .containing("import { useTranslation } from 'react-i18next")
      .containing("const { t } = useTranslation();")
      .containing("{t('translationEnabled')}")
      .and()
      .hasPrefixedFiles("src/main/webapp/assets/locales/", "en/translation.json", "fr/translation.json")
      .hasFile("src/test/webapp/unit/home/infrastructure/primary/HomePage.spec.tsx")
      .containing("describe('Home I18next', () => {")
      .and()
      .hasFile("vitest.config.ts")
      .containing("'src/main/webapp/app/*.ts(x)',");
  }

  private ModuleFile app() {
    return file("src/test/resources/projects/react-app/HomePage.tsx", HOME_PAGE_TSX);
  }

  private ModuleFile appTest() {
    return file(
      "src/test/resources/projects/react-app/HomePage.spec.tsx",
      "src/test/webapp/unit/home/infrastructure/primary/HomePage.spec.tsx"
    );
  }

  private ModuleFile index() {
    return file("src/test/resources/projects/react-app/index.tsx", "src/main/webapp/app/index.tsx");
  }

  private ModuleFile vitest() {
    return file("src/test/resources/projects/react-app/vitest.config.ts.template", "./vitest.config.ts");
  }
}
