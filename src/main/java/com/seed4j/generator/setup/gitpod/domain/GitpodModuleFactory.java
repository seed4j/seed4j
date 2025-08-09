package com.seed4j.generator.setup.gitpod.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitpodModuleFactory {

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(from("setup/gitpod"), to("."))
          .addTemplate(".gitpod.yml")
          .addFile(".gitpod.Dockerfile")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
