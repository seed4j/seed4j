package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleOperation(String operation) {
  public JHipsterModuleOperation {
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
