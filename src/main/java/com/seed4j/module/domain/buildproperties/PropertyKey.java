package com.seed4j.module.domain.buildproperties;

import com.seed4j.shared.error.domain.Assert;

public record PropertyKey(String key) {
  public PropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
