package tech.jhipster.lite.dsl.domain.clazz.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldName(String name) {
  public FieldName {
    Assert.field("name", name).noWhitespace().maxLength(50);
  }

  public String get() {
    return name();
  }
}
