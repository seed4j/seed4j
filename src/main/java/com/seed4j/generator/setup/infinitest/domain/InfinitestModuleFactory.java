package com.seed4j.generator.setup.infinitest.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class InfinitestModuleFactory {

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .gitIgnore()
        .pattern("infinitest.filters")
        .and()
      .files()
        .add(from("infinitest/template-infinitest.filters"), to("infinitest.filters"))
        .and()
      .build();
    // @formatter:on
  }
}
