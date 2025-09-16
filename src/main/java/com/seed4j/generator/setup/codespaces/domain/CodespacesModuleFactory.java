package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CodespacesModuleFactory {

  private static final Seed4JSource SOURCE = from("setup/codespaces");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
