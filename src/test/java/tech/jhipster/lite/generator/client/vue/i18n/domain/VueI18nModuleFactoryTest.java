package tech.jhipster.lite.generator.client.vue.i18n.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class VueI18nModuleFactoryTest {

  private static final VueI18nModuleFactory factory = new VueI18nModuleFactory();

  @Test
  void shouldBuildI18nModule() {
    JHipsterModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build()
    );

    JHipsterModuleAsserter asserter = assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      mainFile(),
      homepage(),
      homepageTest(),
      vitest()
    );
    asserter
      .hasFile("package.json")
      .containing(nodeDependency("i18next"))
      .containing(nodeDependency("i18next-vue"))
      .containing(nodeDependency("i18next-browser-languagedetector"))
      .and()
      .hasFile("src/main/webapp/app/i18n.ts")
      .and()
      .hasFile("src/test/webapp/unit/i18n.spec.ts")
      .and()
      .hasFile("src/main/webapp/app/Translations.ts")
      .and()
      .hasFile("src/main/webapp/app/home/HomeTranslations.ts")
      .and()
      .hasFile("src/main/webapp/app/main.ts")
      .containing("import i18next from './i18n';")
      .containing("import I18NextVue from 'i18next-vue';")
      .containing("app.use(I18NextVue, { i18next });")
      .and()
      .hasFile("src/main/webapp/app/home/infrastructure/primary/HomepageVue.vue")
      .containing("<h2>{{ $t('home.translationEnabled') }}</h2>")
      .and()
      .hasFile("src/test/setupTests.ts")
      .and()
      .hasFile("vitest.config.ts")
      .containing("setupFiles: ['./src/test/setupTests.ts']")
      .and()
      .hasFile("src/main/webapp/app/home/locales/en.ts")
      .and()
      .hasFile("src/main/webapp/app/home/locales/fr.ts")
      .and()
      .hasFile("src/test/webapp/unit/home/infrastructure/primary/HomepageVue.spec.ts");
  }

  private ModuleFile mainFile() {
    return file("src/test/resources/projects/vue/main.ts.template", "src/main/webapp/app/main.ts");
  }

  private ModuleFile homepage() {
    return file(
      "src/test/resources/projects/vue/HomepageVue.vue.template",
      "src/main/webapp/app/home/infrastructure/primary/HomepageVue.vue"
    );
  }

  private ModuleFile homepageTest() {
    return file(
      "src/test/resources/projects/vue/HomepageVue.spec.ts.template",
      "src/test/webapp/unit/home/infrastructure/primary/HomepageVue.spec.ts"
    );
  }

  private ModuleFile vitest() {
    return file("src/test/resources/projects/vue/vitest.config.ts.template", "./vitest.config.ts");
  }
}
