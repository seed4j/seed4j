package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleOperation(String operation) {
  public JHipsterModuleOperation {
    Assert.notBlank("operation", operation);
  }

  public String get() {
    return operation();
  }
}
