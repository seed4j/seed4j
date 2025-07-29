package com.seed4j.generator.client.vue.i18n.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import org.junit.jupiter.api.Test;

@UnitTest
class VueI18nModuleFactoryTest {

  private static final VueI18nModuleFactory factory = new VueI18nModuleFactory();

  @Test
  void shouldBuildI18nModule() {
    JHipsterModule module = factory.buildModule(
      JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("jhipster").build()
    );

    JHipsterModuleAsserter asserter = assertThatModuleWithFiles(module, packageJsonFile(), mainFile(), homepage(), vitest());
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
      .hasFile("src/main/webapp/app/home/locales/fr.ts");
  }

  private ModuleFile mainFile() {
    return file("src/test/resources/projects/vue/main.ts.mustache", "src/main/webapp/app/main.ts");
  }

  private ModuleFile homepage() {
    return file(
      "src/test/resources/projects/vue/HomepageVue.vue.mustache",
      "src/main/webapp/app/home/infrastructure/primary/HomepageVue.vue"
    );
  }

  private ModuleFile vitest() {
    return file("src/test/resources/projects/vue/vitest.config.ts.mustache", "./vitest.config.ts");
  }
}
