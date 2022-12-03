package tech.jhipster.lite.dsl.generator.clazz.domain;

import tech.jhipster.lite.error.domain.Assert;

public record FieldValidatorMinSize(Integer min) implements AnnotationMin {
  public FieldValidatorMinSize {
    Assert.notNull("min", min);
  }
  public String name() {
    return "Size";
  }
}
