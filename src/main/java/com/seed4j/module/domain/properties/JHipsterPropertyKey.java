package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;

public record JHipsterPropertyKey(String key) {
  public JHipsterPropertyKey {
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
