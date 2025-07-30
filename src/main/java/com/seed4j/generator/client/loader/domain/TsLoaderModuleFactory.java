package com.seed4j.generator.client.loader.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TsLoaderModuleFactory {

  private static final JHipsterSource SOURCE = from("client/loader");
  private static final String DESTINATION = "shared/loader/infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
