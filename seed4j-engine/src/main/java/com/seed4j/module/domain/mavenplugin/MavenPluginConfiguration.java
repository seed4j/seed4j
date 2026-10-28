package com.seed4j.module.domain.mavenplugin;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record MavenPluginConfiguration(String configuration) {
  public MavenPluginConfiguration {
    Assert.notBlank("configuration", configuration);
  }

  public String get() {
    return configuration();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return get();
  }
}
