package tech.jhipster.lite.dsl.generator.clazz.domain;

import tech.jhipster.lite.error.domain.Assert;

public record FieldAnnotationGeneric(String name) implements Annotation {
  public FieldAnnotationGeneric {
    Assert.field("name", name).noWhitespace();
  }
}
