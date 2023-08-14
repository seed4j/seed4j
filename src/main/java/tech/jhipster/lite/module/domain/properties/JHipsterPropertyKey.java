package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterPropertyKey(String key) {
  public JHipsterPropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
