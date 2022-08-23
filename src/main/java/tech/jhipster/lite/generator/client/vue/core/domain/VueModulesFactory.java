package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class VueModulesFactory {

  private static final JHipsterSource SOURCE = from("client/vue");
  private static final JHipsterSource IMAGE_SOURCE = SOURCE.append("webapp/content/images");
  private static final JHipsterSource COMMON_PRIMARY_SOURCE = SOURCE.append("webapp/app/common/primary");
  private static final JHipsterSource COMMON_PRIMARY_TEST_SOURCE = SOURCE.append("test/spec/common/primary");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final JHipsterDestination MAIN_PRIMARY_DESTINATION = MAIN_DESTINATION.append("common/primary");
  private static final JHipsterDestination COMMON_PRIMARY_TEST_DESTINATION = to("src/test/javascript/spec/common/primary");

  private static final String IMPORT_NEEDLE = "// jhipster-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";

  public JHipsterModule buildVueModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return ClientsModulesFactory.clientModuleBuilder(properties)
      .packageJson()
        .addDependency(packageName("vue"), VUE)
        .addDependency(packageName("axios"), COMMON)
        .addDependency(packageName("vue-router"), VUE)
        .addDevDependency(packageName("@rushstack/eslint-patch"), VUE)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/parser"), VUE)
        .addDevDependency(packageName("@vitejs/plugin-vue"), VUE)
        .addDevDependency(packageName("@vue/eslint-config-typescript"), VUE)
        .addDevDependency(packageName("@vue/eslint-config-prettier"), VUE)
        .addDevDependency(packageName("@vue/test-utils"), VUE)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("eslint-plugin-vue"), VUE)
        .addDevDependency(packageName("jest"), COMMON)
        .addDevDependency(packageName("jest-sonar-reporter"), COMMON)
        .addDevDependency(packageName("jest-transform-stub"), VUE)
        .addDevDependency(packageName("ts-jest"), VUE)
        .addDevDependency(packageName("typescript"), COMMON)
        .addDevDependency(packageName("vite"), VUE)
        .addDevDependency(packageName("vue-jest"), VUE)
        .addDevDependency(packageName("vue-tsc"), VUE)
        .addDevDependency(packageName("@types/sinon"), VUE)
        .addDevDependency(packageName("sinon"), VUE)
        .addScript(scriptKey("build"), scriptCommand("vue-tsc -p tsconfig.build.json --noEmit && vite build --emptyOutDir"))
        .addScript(scriptKey("dev"), scriptCommand("vite"))
        .addScript(scriptKey("jest"), scriptCommand("jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("test"), scriptCommand("npm run jest --"))
        .addScript(scriptKey("test:watch"), scriptCommand("npm run jest -- --watch"))
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.js"), to(".eslintrc.js"))
        .add(SOURCE.file("jest.config.js"), to("jest.config.js"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.build.json"), to("tsconfig.build.json"))
        .add(SOURCE.file("vite.config.ts"), to("vite.config.ts"))
        .add(SOURCE.template("webapp/app/http/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("http/AxiosHttp.ts"))
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
        .batch(COMMON_PRIMARY_SOURCE.append("app"), MAIN_PRIMARY_DESTINATION.append("app"))
          .addTemplate("App.component.ts")
          .addTemplate("App.html")
          .addTemplate("App.vue")
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
          .addTemplate("Homepage.vue")
          .addTemplate("index.ts")
          .and()
        .add(COMMON_PRIMARY_TEST_SOURCE.template("homepage/Homepage.spec.ts"), COMMON_PRIMARY_TEST_DESTINATION.append("homepage/Homepage.spec.ts"))
        .add(SOURCE.template("webapp/app/router/router.ts"), MAIN_DESTINATION.append("router/router.ts"))
        .add(SOURCE.template("test/spec/router/Router.spec.ts"), to("src/test/javascript/spec/router/Router.spec.ts"))
        .batch(SOURCE.append("webapp/app/common/domain"), MAIN_DESTINATION.append("common/domain"))
          .addTemplate("Logger.ts")
          .addTemplate("Message.ts")
          .and()
        .add(SOURCE.template("webapp/app/common/secondary/ConsoleLogger.ts"), MAIN_DESTINATION.append("common/secondary/ConsoleLogger.ts"))
        .add(SOURCE.template("test/spec/common/domain/Logger.fixture.ts"), to("src/test/javascript/spec/common/domain/Logger.fixture.ts"))
        .add(SOURCE.template("test/spec/common/secondary/ConsoleLogger.spec.ts"), to("src/test/javascript/spec/common/secondary/ConsoleLogger.spec.ts"))
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
        .in("src/main/webapp/app/main.ts")
          .add(lineBeforeText(IMPORT_NEEDLE), piniaImports())
          .add(lineBeforeText(PROVIDER_NEEDLE), piniaProvider())
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String piniaImports() {
    return """
        import { createPinia } from 'pinia';
        import piniaPersist from 'pinia-plugin-persist';
        """;
  }

  private String piniaProvider() {
    return """
        const pinia = createPinia();
        pinia.use(piniaPersist);
        app.use(pinia);
        """;
  }
}
