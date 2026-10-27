package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.Assert;

public record NodePackageName(String packageName) {
  public NodePackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }

  @Override
  public String toString() {
    return packageName;
  }
}
