package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record NodePackageVersion(String version) {
  public NodePackageVersion {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }

  public String majorVersion() {
    return version().split("\\.", -1)[0];
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return version;
  }
}
