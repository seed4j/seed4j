package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache;

import java.util.List;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;

public class AnnotationBuilder {

  public List<AnnotationSimple> getAnnotationSimpleFrom(List<Annotation> annotation) {
    return annotation.stream().filter(s -> s.value().isEmpty()).map(AnnotationSimple::from).toList();
  }

  public List<AnnotationValue> getAnnotationWithValueFrom(List<Annotation> annotation) {
    return annotation.stream().filter(annot -> annot.value().isPresent()).map(AnnotationValue::from).toList();
  }
}
