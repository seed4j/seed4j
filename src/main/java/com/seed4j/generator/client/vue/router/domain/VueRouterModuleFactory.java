package com.seed4j.generator.client.vue.router.domain;

import static com.seed4j.generator.typescript.common.domain.VitestShortcuts.vitestCoverageExclusion;
import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.VUE;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import java.util.function.Consumer;

public class VueRouterModuleFactory {

  private static final Seed4JSource SOURCE = from("client/vue");
  private static final Seed4JSource APP_SOURCE = from("client/vue/webapp/app");

  private static final Seed4JDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final Seed4JDestination TEST_DESTINATION = to("src/test/webapp");

  private static final String IMPORT_NEEDLE = "// seed4j-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// seed4j-needle-main-ts-provider";
  private static final String ROUTER_IMPORTS = "import router from './router';";
  private static final String ROUTER_PROVIDER = "app.use(router);";

  private static final String EXPORT_DEFAULT_APP_VUE = """
      export default {
        name: 'AppVue',
      };\
    """;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("vue-router"), VUE)
        .and()
      .files()
        .batch(APP_SOURCE, MAIN_DESTINATION)
          .addTemplate("router.ts")
        .and()
        .batch(APP_SOURCE.append("home"), MAIN_DESTINATION.append("home"))
          .addTemplate("application/HomeRouter.ts")
        .and()
        .add(SOURCE.template("webapp/app/router.ts"), MAIN_DESTINATION.append("router.ts"))
        .add(APP_SOURCE.template("test/webapp/unit/router/infrastructure/primary/HomeRouter.spec.ts.mustache"), TEST_DESTINATION.append("unit/router/infrastructure/primary/HomeRouter.spec.ts"))
        .and()
        .mandatoryReplacements()
          .in(path("src/main/webapp/app/main.ts"))
            .add(lineBeforeText(IMPORT_NEEDLE), ROUTER_IMPORTS)
            .add(lineBeforeText(PROVIDER_NEEDLE), ROUTER_PROVIDER)
            .and()
          .in(path("src/main/webapp/app/AppVue.vue"))
            .add(text("<HomepageVue />"), "<router-view />")
            .add(text("<script setup lang=\"ts\">"), "<script lang=\"ts\">")
            .add(text("import HomepageVue from './home/infrastructure/primary/HomepageVue.vue'"), EXPORT_DEFAULT_APP_VUE)
            .and()
          .and()
      .apply(patchVitestConfig())
      .build();
    // @formatter:on
  }

  private Consumer<Seed4JModuleBuilder> patchVitestConfig() {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .apply(vitestCoverageExclusion("src/main/webapp/app/router.ts"));
    // @formatter:on
  }
}
