package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CodespacesModuleFactory {

  private static final SeedSource SOURCE = from("setup/codespaces");

  public SeedModule buildModule(SeedModuleProperties properties) {
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
