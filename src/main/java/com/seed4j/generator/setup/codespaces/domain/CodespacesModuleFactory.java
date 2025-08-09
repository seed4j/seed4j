package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CodespacesModuleFactory {

  private static final SeedSource SOURCE = from("setup/codespaces");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE, to(".devcontainer"))
          .addTemplate("devcontainer.json")
          .addFile("Dockerfile")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
