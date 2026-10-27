package com.seed4j.module.domain.buildproperties;

import com.seed4j.shared.error.domain.Assert;

public record BuildProperty(PropertyKey key, PropertyValue value) {
  public BuildProperty {
    Assert.notNull("key", key);
    Assert.notNull("value", value);
  }
}
