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
  private static final JHipsterSource APP_SOURCE = from("client/vue/webapp/app");
  private static final JHipsterSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");
  private static final JHipsterSource IMAGE_SOURCE = SOURCE.append("webapp/content/images");
  private static final JHipsterSource SOURCE_COMMON = from("client/common");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");

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
        .addDevDependency(packageName("piqure"), VUE)
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
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .addTemplate("vitest.config.ts")
          .and()
        .batch(SOURCE_COMMON, to("."))
          .addFile(".npmrc")
          .and()
        .add(SOURCE.template("webapp/index.html"), to("src/main/webapp/index.html"))
        .batch(APP_SOURCE, MAIN_DESTINATION)
          .addTemplate("env.d.ts")
          .addTemplate("main.ts")
          .addTemplate("AppVue.vue")
          .addTemplate("injections.ts")
          .addTemplate("router.ts")
          .and()
        .batch(APP_SOURCE.append("home"), MAIN_DESTINATION.append("home"))
          .addTemplate("application/HomeRouter.ts")
          .addTemplate("infrastructure/primary/HomepageVue.vue")
          .and()
        .add(APP_SOURCE.template("shared/http/infrastructure/secondary/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("shared/http/infrastructure/secondary/AxiosHttp.ts"))
        .batch(IMAGE_SOURCE, to("src/main/webapp/content/images"))
          .addFile("JHipster-Lite-neon-green.png")
          .addFile("VueLogo.png")
          .and()
        .add(SOURCE.template("webapp/app/router.ts"), MAIN_DESTINATION.append("router.ts"))
        .add(SOURCE.template("Dummy.spec.ts"), to("src/test/webapp/unit/Dummy.spec.ts"))
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
