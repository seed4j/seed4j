package tech.jhipster.lite.dsl.generator.clazz.domain;

import tech.jhipster.lite.error.domain.Assert;

public record FieldValidatorSize(Integer min, Integer max) implements AnnotationMax, AnnotationMin {
  public FieldValidatorSize {
    Assert.notNull("min", min);
    Assert.notNull("max", max);
  }
  public String name() {
    return "Size";
  }
}
