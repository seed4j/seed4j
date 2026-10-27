package com.seed4j.module.domain.javabuild.command;

import com.seed4j.shared.error.domain.Assert;

public record AddGradleConfiguration(String configuration) implements JavaBuildCommand {
  public AddGradleConfiguration {
    Assert.notNull("configuration", configuration);
  }

  public String get() {
    return configuration;
  }
}
