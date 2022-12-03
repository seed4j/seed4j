package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldType(String type) {
  public FieldType {
    Assert.field("type", type).noWhitespace();
    type = capitalize(type);
  }

  public String get() {
    return type();
  }

  private String capitalize(String value) {
    return value.substring(0, 1).toUpperCase() + value.substring(1);
  }
}
