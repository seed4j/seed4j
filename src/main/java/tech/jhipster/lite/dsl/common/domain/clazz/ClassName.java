package tech.jhipster.lite.dsl.common.domain.clazz;

import tech.jhipster.lite.error.domain.Assert;

public record ClassName(String name) {
  public ClassName {
    Assert.field("key", name).noWhitespace().maxLength(50);
    name = capitalize(name);
  }

  public String get() {
    return name();
  }
  private String capitalize(String value) {
    return value.substring(0, 1).toUpperCase() + value.substring(1);
  }
}
