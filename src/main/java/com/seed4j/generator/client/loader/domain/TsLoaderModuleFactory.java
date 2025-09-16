package com.seed4j.generator.client.loader.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TsLoaderModuleFactory {

  private static final Seed4JSource SOURCE = from("client/loader");
  private static final String DESTINATION = "shared/loader/infrastructure/primary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("Loader.ts"), to("src/main/webapp/app").append(DESTINATION).append("Loader.ts"))
        .add(SOURCE.template("Loader.spec.ts"), to("src/test/webapp/unit").append(DESTINATION).append("Loader.spec.ts"))
        .and()
      .build();
    // @formatter:on
  }
}
