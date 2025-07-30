package com.seed4j.generator.client.vue.i18n.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineAfterRegex;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.VUE;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class VueI18nModuleFactory {

  private static final JHipsterSource APP_SOURCE = from("client/common/i18n");
  private static final JHipsterSource HOME_CONTEXT_SOURCE = from("client/common/i18n/app");
  private static final JHipsterSource ASSETS_SOURCE = from("client/common/i18n/app/locales");
  private static final JHipsterSource TEST_SOURCE = from("client/vue/i18n/src/test");

  private static final String INDEX = "src/main/webapp/app/";
  private static final String INDEX_TEST = "src/test/";

  private static final String PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("i18next"), COMMON)
        .addDependency(packageName("i18next-vue"), VUE)
        .addDependency(packageName("i18next-browser-languagedetector"), COMMON)
        .and()
      .files()
      .batch(APP_SOURCE, to(INDEX))
        .addFile("i18n.ts")
        .addFile("Translations.ts")
        .and()
      .batch(HOME_CONTEXT_SOURCE, to(INDEX + "home/"))
        .addFile("HomeTranslations.ts")
        .and()
      .batch(ASSETS_SOURCE, to(INDEX + "home/locales/"))
        .addFile("en.ts")
        .addFile("fr.ts")
      .and()
      .batch(TEST_SOURCE, to(INDEX_TEST))
        .addFile("setupTests.ts")
        .and()
      .batch(APP_SOURCE, to(INDEX_TEST + "webapp/unit"))
        .addFile("i18n.spec.ts")
        .and()
      .and()
      .mandatoryReplacements()
        .in(path(INDEX + "main.ts"))
          .add(lineBeforeText("// jhipster-needle-main-ts-import"), "import i18next from './i18n';")
          .add(lineBeforeText("// jhipster-needle-main-ts-import"), "import I18NextVue from 'i18next-vue';")
          .add(lineBeforeText(PROVIDER_NEEDLE
          ), "app.use(I18NextVue, { i18next });")
          .and()
        .in(path("./vitest.config.ts"))
          .add(lineAfterRegex("test:"), properties.indentation().times(2) + "setupFiles: ['./src/test/setupTests.ts'],")
          .and()
        .and()
      .optionalReplacements()
        .in(path(INDEX + "/home/infrastructure/primary/HomepageVue.vue"))
          .add(lineAfterRegex("Vue \\+ TypeScript \\+ Vite"), properties.indentation().times(2) + "<h2>{{ $t('home.translationEnabled') }}</h2>")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
