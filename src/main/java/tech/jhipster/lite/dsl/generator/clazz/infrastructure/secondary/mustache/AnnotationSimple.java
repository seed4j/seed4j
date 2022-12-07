package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary.mustache;

import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationSimple(String name) {
  public AnnotationSimple {
    Assert.field("name", name).noWhitespace();
  }
  public static AnnotationSimple from(Annotation annotation) {
    return new AnnotationSimple(annotation.name());
  }
}
