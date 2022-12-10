package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache;

import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationValue(String name, String value) {
  public AnnotationValue {
    Assert.field("key", name).noWhitespace();
    Assert.field("value", value).notNull();
  }
  public static AnnotationValue from(Annotation annotation) {
    return new AnnotationValue(annotation.name(), annotation.value().orElse(""));
  }
}
