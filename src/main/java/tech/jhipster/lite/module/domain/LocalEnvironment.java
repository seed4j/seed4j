package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.shared.error.domain.Assert;

public record LocalEnvironment(String localEnvironment) {
  public LocalEnvironment {
    Assert.notBlank("localEnvironment", localEnvironment);
  }

  public String get() {
    return localEnvironment();
  }
}
