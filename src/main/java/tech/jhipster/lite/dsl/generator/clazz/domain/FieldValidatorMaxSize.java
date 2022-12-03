package tech.jhipster.lite.dsl.generator.clazz.domain;

import tech.jhipster.lite.error.domain.Assert;

public record FieldValidatorMaxSize(Integer max) implements AnnotationMax {
  public FieldValidatorMaxSize {
    Assert.notNull("max", max);
  }
  public String name() {
    return "Size";
  }
}
