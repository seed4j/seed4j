package com.seed4j.generator.setup.infinitest.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class InfinitestModuleFactory {

  public SeedModule buildModule(SeedModuleProperties properties) {
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
