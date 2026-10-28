package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JPropertyKey(String key) {
  public Seed4JPropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }

  @Override
  public String toString() {
    return key();
  }
}
