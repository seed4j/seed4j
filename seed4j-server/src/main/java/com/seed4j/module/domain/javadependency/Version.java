package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record Version(String version) {
  public Version {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return get();
  }
}
