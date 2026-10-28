package com.seed4j.module.domain.buildproperties;

import com.seed4j.shared.error.domain.Assert;

public record PropertyValue(String value) {
  public PropertyValue {
    Assert.notNull("value", value);
  }

  public String get() {
    return value();
  }
}
