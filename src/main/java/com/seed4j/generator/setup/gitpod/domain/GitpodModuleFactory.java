package com.seed4j.generator.setup.gitpod.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitpodModuleFactory {

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
