package tech.jhipster.lite.dsl.common.domain.clazz.enums;

import tech.jhipster.lite.error.domain.Assert;

public record EnumKey(String key) {
  public EnumKey {
    Assert.field("key", key).noWhitespace();
    key = key.toUpperCase();
  }

  public String get() {
    return key();
  }
}
