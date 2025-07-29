package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;

public record LocalEnvironment(String localEnvironment) {
  public LocalEnvironment {
    Assert.notBlank("localEnvironment", localEnvironment);
  }

  public String get() {
    return localEnvironment();
  }
}
