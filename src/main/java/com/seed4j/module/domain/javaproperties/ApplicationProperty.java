package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;

public record ApplicationProperty(ApplicationPropertiesFile file, PropertyKey key, PropertyValue value) {
  public ApplicationProperty {
    Assert.notNull("file", file);
    Assert.notNull("key", key);
    Assert.notNull("value", value);
  }
}
