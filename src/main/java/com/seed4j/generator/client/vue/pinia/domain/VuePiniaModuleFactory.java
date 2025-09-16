package com.seed4j.generator.client.vue.pinia.domain;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.VUE;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class VuePiniaModuleFactory {

  private static final String IMPORT_NEEDLE = "// seed4j-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// seed4j-needle-main-ts-provider";
  private static final String PINIA_IMPORTS = """
    import { createPinia } from 'pinia';
    import piniaPersist from 'pinia-plugin-persistedstate';
    """;
  private static final String PINIA_PROVIDER = """
    const pinia = createPinia();
    pinia.use(piniaPersist);
    app.use(pinia);
    """;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
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
    // @formatter:on
  }
}
