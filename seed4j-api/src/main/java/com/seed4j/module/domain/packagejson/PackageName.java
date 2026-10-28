package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;

public record PackageName(String packageName) {
  public PackageName {
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
