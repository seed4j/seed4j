package tech.jhipster.lite.dsl.common.domain.clazz.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldName(String name) {
  public FieldName {
    Assert.field("key", name).noWhitespace().maxLength(50);
  }

  public String get() {
    return name();
  }
}
