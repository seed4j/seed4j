package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class VueModulesFactory {

  private static final JHipsterSource SOURCE = from("client/vue");
  private static final JHipsterSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");
  private static final JHipsterSource COMMON_ESLINT = from("client/common/eslint");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");

  private static final String IMPORT_NEEDLE = "// jhipster-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";
  private static final String PINIA_IMPORTS =
    """
    import { createPinia } from 'pinia';
    import piniaPersist from 'pinia-plugin-persist';
    """;
  private static final String PINIA_PROVIDER =
    """
    const pinia = createPinia();
    pinia.use(piniaPersist);
    app.use(pinia);
    """;

  public JHipsterModule buildVueModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return ClientsModulesFactory.clientModuleBuilder(properties)
      .documentation(documentationTitle("Vue"), DOCUMENTATION_SOURCE.file("vue.md"))
      .packageJson()
        .addDependency(packageName("vue"), VUE)
        .addDependency(packageName("axios"), VUE)
        .addDevDependency(packageName("@rushstack/eslint-patch"), VUE)
        .addDevDependency(packageName("@typescript-eslint/parser"), VUE)
        .addDevDependency(packageName("@vitejs/plugin-vue"), VUE)
        .addDevDependency(packageName("@vue/eslint-config-typescript"), VUE)
        .addDevDependency(packageName("@vue/eslint-config-prettier"), VUE)
        .addDevDependency(packageName("@vue/test-utils"), VUE)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), VUE)
        .addDevDependency(packageName("eslint"), VUE)
        .addDevDependency(packageName("eslint-plugin-vue"), VUE)
        .addDevDependency(packageName("jsdom"), VUE)
        .addDevDependency(packageName("typescript"), VUE)
        .addDevDependency(packageName("vite"), VUE)
        .addDevDependency(packageName("vitest"), VUE)
        .addDevDependency(packageName("vitest-sonar-reporter"), VUE)
        .addDevDependency(packageName("vue-tsc"), VUE)
        .addDevDependency(packageName("@types/sinon"), VUE)
        .addDevDependency(packageName("sinon"), VUE)
        .addScript(scriptKey("build"), scriptCommand("vue-tsc -p tsconfig.build.json --noEmit && vite build --emptyOutDir"))
        .addScript(scriptKey("dev"), scriptCommand("vite"))
        .addScript(scriptKey("lint"), scriptCommand("eslint --ext .js,.ts,.vue src"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("test"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("test:watch"), scriptCommand("vitest --"))
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.js"), to(".eslintrc.js"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.build.json"), to("tsconfig.build.json"))
        .add(SOURCE.file("vite.config.ts"), to("vite.config.ts"))
        .add(SOURCE.file("vitest.config.ts"), to("vitest.config.ts"))
        .add(SOURCE.template("webapp/app/http/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("http/AxiosHttp.ts"))
        .add(COMMON_ESLINT.file(".eslintignore"), to(".eslintignore"))
        .batch(SOURCE.file("test/spec/http"), to("src/test/javascript/spec/http"))
          .addTemplate("AxiosHttp.spec.ts")
          .addTemplate("AxiosHttpStub.ts")
          .addTemplate("AxiosStub.ts")
          .and()
        .add(SOURCE.template("webapp/index.html"), to("src/main/webapp/index.html"))
        .batch(SOURCE.append("webapp/app"), MAIN_DESTINATION)
          .addTemplate("env.d.ts")
          .addTemplate("main.ts")
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
        .addDependency(packageName("pinia-plugin-persist"), VUE)
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
