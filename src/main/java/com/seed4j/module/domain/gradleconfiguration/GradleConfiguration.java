package com.seed4j.module.domain.gradleconfiguration;

import com.seed4j.shared.error.domain.Assert;

public record GradleConfiguration(String configuration) {
  public GradleConfiguration {
    Assert.notBlank("configuration", configuration);
  }

  public String get() {
    return configuration();
  }
}
