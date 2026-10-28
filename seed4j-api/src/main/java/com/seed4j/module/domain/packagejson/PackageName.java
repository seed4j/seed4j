package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record PackageName(String packageName) {
  public PackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return packageName;
  }
}
