package tech.jhipster.lite.dsl.parser.domain.clazz;

import tech.jhipster.lite.error.domain.Assert;

public record DslContextName(String name) {
  public DslContextName {
    Assert.field("name", name).noWhitespace().maxLength(50);
    name = lowercase(name);
  }

  public String get() {
    return name();
  }

  private String lowercase(String value) {
    return value.toLowerCase();
  }
}
