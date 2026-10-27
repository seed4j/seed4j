package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;

public record Comment(String value) {
  public Comment {
    Assert.notBlank("value", value);
  }

  public String get() {
    return value();
  }
}
