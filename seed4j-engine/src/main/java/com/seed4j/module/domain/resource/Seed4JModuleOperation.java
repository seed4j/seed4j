package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleOperation(String operation) {
  public Seed4JModuleOperation {
    Assert.notBlank("operation", operation);
  }

  public String get() {
    return operation();
  }

  @Override
  public String toString() {
    return operation();
  }
}
