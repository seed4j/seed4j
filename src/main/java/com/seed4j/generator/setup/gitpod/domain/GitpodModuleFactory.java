package com.seed4j.generator.setup.gitpod.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitpodModuleFactory {

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
