package tech.jhipster.lite.dsl.common.domain.clazz.enums;

import tech.jhipster.lite.error.domain.Assert;

public record EnumName(String name) {
  public EnumName {
    Assert.field("name", name).noWhitespace().maxLength(50);
    name = capitalize(name);
  }

  public String get() {
    return name();
  }
  private String capitalize(String value) {
    return value.substring(0, 1).toUpperCase() + value.substring(1);
  }
}
