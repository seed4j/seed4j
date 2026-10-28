package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;

public record ApplicationPropertyComment(ApplicationPropertiesFile file, PropertyKey key, Comment value) {
  public ApplicationPropertyComment {
    Assert.notNull("file", file);
    Assert.notNull("key", key);
    Assert.notNull("value", value);
  }
}
