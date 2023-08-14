package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.shared.error.domain.Assert;

public record PropertyKey(String key) {
  public PropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
