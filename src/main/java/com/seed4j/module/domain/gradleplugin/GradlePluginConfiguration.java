package com.seed4j.module.domain.gradleplugin;

import com.seed4j.shared.error.domain.Assert;

public record GradlePluginConfiguration(String configuration) {
  public GradlePluginConfiguration {
    Assert.notBlank("configuration", configuration);
  }

  public String get() {
    return configuration();
  }
}
