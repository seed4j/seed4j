package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;

public record PropertyKey(String key) implements Comparable<PropertyKey> {
  public PropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }

  @Override
  public int compareTo(PropertyKey other) {
    return key.compareTo(other.key);
  }
}
