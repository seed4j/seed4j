package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.shared.error.domain.Assert;

public record ScriptKey(String key) {
  public ScriptKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
