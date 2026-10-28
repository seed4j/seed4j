package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;

public record Version(String version) {
  public Version {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }

  @Override
  public String toString() {
    return get();
  }
}
