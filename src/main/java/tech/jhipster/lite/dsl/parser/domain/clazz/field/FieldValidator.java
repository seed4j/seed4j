package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldValidator(String name, Integer value) {
  public FieldValidator {
    Assert.field("name", name).notNull().notBlank();
    Assert.field("value", value).positive();
  }
}
