package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.NodeModuleFormat.MODULE;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.VUE;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class VueModulesFactory {

  private static final JHipsterSource SOURCE = from("client/vue");
  private static final JHipsterSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test/unit");
  private static final JHipsterSource IMAGE_SOURCE = SOURCE.append("webapp/content/images");
  private static final JHipsterSource COMMON_PRIMARY_SOURCE = SOURCE.append("webapp/app/common/primary");
  private static final JHipsterSource COMMON_PRIMARY_TEST_SOURCE = SOURCE.append("test/unit/common/primary");
  private static final JHipsterSource SOURCE_COMMON = from("client/common");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final JHipsterDestination TEST_DESTINATION = to("src/test/webapp/unit");
  private static final JHipsterDestination MAIN_PRIMARY_DESTINATION = MAIN_DESTINATION.append("common/primary");
  private static final JHipsterDestination COMMON_PRIMARY_TEST_DESTINATION = to("src/test/webapp/unit/common/primary");

  private static final String IMPORT_NEEDLE = "// jhipster-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";
  private static final String PINIA_IMPORTS =
    """
    import { createPinia } from 'pinia';
    import piniaPersist from 'pinia-plugin-persistedstate';
    """;
  private static final String PINIA_PROVIDER =
    """
    const pinia = createPinia();
    pinia.use(piniaPersist);
    app.use(pinia);
    """;

  public JHipsterModule buildVueModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .optionalReplacements()
        .in(path(".lintstagedrc.cjs"))
          .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.{ts,vue}': ['eslint --fix', 'prettier --write'],")
          .and()
        .and()
      .documentation(documentationTitle("Vue"), DOCUMENTATION_SOURCE.file("vue.md"))
      .packageJson()
        .type(MODULE)
        .addDependency(packageName("vue"), VUE)
        .addDependency(packageName("axios"), VUE)
        .addDependency(packageName("vue-router"), VUE)
        .addDevDependency(packageName("@typescript-eslint/parser"), COMMON)
        .addDevDependency(packageName("@vitejs/plugin-vue"), VUE)
        .addDevDependency(packageName("typescript-eslint"), COMMON)
        .addDevDependency(packageName("globals"), COMMON)
        .addDevDependency(packageName("@vue/test-utils"), VUE)
        .addDevDependency(packageName("@vue/tsconfig"), VUE)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("eslint-config-prettier"), COMMON)
        .addDevDependency(packageName("eslint-plugin-vue"), VUE)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("typescript"), COMMON)
        .addDevDependency(packageName("vite"), COMMON)
        .addDevDependency(packageName("vite-tsconfig-paths"), COMMON)
        .addDevDependency(packageName("vitest"), COMMON)
        .addDevDependency(packageName("vitest-sonar-reporter"), COMMON)
        .addDevDependency(packageName("vue-tsc"), VUE)
        .addDevDependency(packageName("@types/sinon"), VUE)
        .addDevDependency(packageName("sinon"), VUE)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:vue"), scriptCommand("vue-tsc -p tsconfig.build.json --noEmit && vite build --emptyOutDir"))
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("lint"), scriptCommand("eslint ."))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("watch:tsc"), scriptCommand("vue-tsc -p tsconfig.build.json --noEmit --watch"))
        .addScript(scriptKey("test"), scriptCommand("npm run watch:test"))
        .addScript(scriptKey("watch:test"), scriptCommand("vitest --"))
        .addScript(scriptKey("test:coverage"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("test:watch"), scriptCommand("vitest --"))
        .and()
      .files()
        .add(SOURCE.template("eslint.config.js.mustache"), to("eslint.config.js"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.build.json"), to("tsconfig.build.json"))
        .add(SOURCE.template("webapp/app/http/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("http/AxiosHttp.ts"))
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .addTemplate("vitest.config.ts")
          .and()
        .batch(SOURCE_COMMON, to("."))
          .addFile(".npmrc")
          .and()
        .batch(SOURCE.file("test/unit/http"), to("src/test/webapp/unit/http"))
          .addTemplate("AxiosHttp.spec.ts")
          .addTemplate("AxiosHttpStub.ts")
          .addTemplate("AxiosStub.ts")
          .and()
        .add(SOURCE.template("webapp/index.html"), to("src/main/webapp/index.html"))
        .batch(SOURCE.append("webapp/app"), MAIN_DESTINATION)
          .addTemplate("env.d.ts")
          .addTemplate("main.ts")
          .and()
        .batch(COMMON_PRIMARY_SOURCE.append("app"), MAIN_PRIMARY_DESTINATION.append("app"))
          .addTemplate("App.component.ts")
          .addTemplate("App.html")
          .addTemplate("AppVue.vue")
          .addTemplate("index.ts")
          .and()
        .batch(IMAGE_SOURCE, to("src/main/webapp/content/images"))
          .addFile("JHipster-Lite-neon-green.png")
          .addFile("VueLogo.png")
          .and()
        .add(COMMON_PRIMARY_TEST_SOURCE.template("app/App.spec.ts"), COMMON_PRIMARY_TEST_DESTINATION.append("app/App.spec.ts"))
        .batch(COMMON_PRIMARY_SOURCE.append("homepage"), MAIN_PRIMARY_DESTINATION.append("homepage"))
          .addTemplate("Homepage.component.ts")
          .addTemplate("Homepage.html")
          .addTemplate("HomepageVue.vue")
          .addTemplate("index.ts")
          .and()
        .add(COMMON_PRIMARY_TEST_SOURCE.template("homepage/Homepage.spec.ts"), COMMON_PRIMARY_TEST_DESTINATION.append("homepage/Homepage.spec.ts"))
        .add(SOURCE.template("webapp/app/router/router.ts"), MAIN_DESTINATION.append("router/router.ts"))
        .add(SOURCE.template("test/unit/router/Router.spec.ts"), to("src/test/webapp/unit/router/Router.spec.ts"))
        .batch(SOURCE.append("webapp/app/common/domain"), MAIN_DESTINATION.append("common/domain"))
          .addTemplate("Logger.ts")
          .addTemplate("Message.ts")
          .and()
        .add(SOURCE.template("webapp/app/common/secondary/ConsoleLogger.ts"), MAIN_DESTINATION.append("common/secondary/ConsoleLogger.ts"))
        .add(SOURCE.template("test/unit/common/domain/Logger.fixture.ts"), to("src/test/webapp/unit/common/domain/Logger.fixture.ts"))
        .add(SOURCE.template("test/unit/common/secondary/ConsoleLogger.spec.ts"), to("src/test/webapp/unit/common/secondary/ConsoleLogger.spec.ts"))
        .add(SOURCE.file("webapp/app/vue/VueProp.ts"), to("src/main/webapp/app/vue/VueProp.ts"))
        .batch(TEST_SOURCE.append("vue/vue-prop"), TEST_DESTINATION.append("vue/vue-prop"))
          .addFile("ArrayComponentVue.vue")
          .addFile("ObjectComponentVue.vue")
          .addFile("VueProp.spec.ts")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildPiniaModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("pinia"), VUE)
        .addDependency(packageName("pinia-plugin-persistedstate"), VUE)
        .addDevDependency(packageName("@pinia/testing"), VUE)
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/app/main.ts"))
          .add(lineBeforeText(IMPORT_NEEDLE), PINIA_IMPORTS)
          .add(lineBeforeText(PROVIDER_NEEDLE), PINIA_PROVIDER)
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
