package tech.jhipster.lite.dsl.common.domain.clazz.enums;

import tech.jhipster.lite.error.domain.Assert;

public record EnumValue(String value) {
  public EnumValue {
    Assert.field("key", value).notNull();
  }

  public String get() {
    return value();
  }
}
