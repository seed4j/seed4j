package com.seed4j.generator.typescript.optional.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class OptionalTypescriptModuleFactory {

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(from("typescript/webapp/common/domain/optional/").file("Optional.ts"), to("src/main/webapp/app/common/domain/Optional.ts"))
        .add(from("typescript/test/webapp/unit/common/domain/optional/").file("Optional.spec.ts"), to("src/test/webapp/unit/common/domain/Optional.spec.ts"))
      .and()
      .build();
    // @formatter:on
  }
}
