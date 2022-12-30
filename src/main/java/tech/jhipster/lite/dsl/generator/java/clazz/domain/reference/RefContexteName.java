package tech.jhipster.lite.dsl.generator.java.clazz.domain.reference;

import tech.jhipster.lite.error.domain.Assert;

public record RefContexteName(String name) {
  public RefContexteName {
    Assert.field("name", name).notNull().notBlank();
  }
}
