package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.error.domain.Assert;

public record DslFrom(String name) {
  public DslFrom {
    Assert.field("name", name).notBlank();
  }
}
