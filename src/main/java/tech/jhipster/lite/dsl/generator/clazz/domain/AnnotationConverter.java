package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.AnnotationBuilder;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.AnnotationConvertionException;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.Constraint;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class AnnotationConverter {

  private final List<Annotation> annotations = new LinkedList<>();

  private final List<String> knowAnnotationWithValue = Stream.of("min", "max", "size", "decimalMin", "decimalMax").toList();

  private final ConfigApp config;

  public AnnotationConverter(ConfigApp config) {
    Assert.notNull("config", config);
    this.config = config;
    annotations.add(AnnotationBuilder.annotationFuture);
    annotations.add(AnnotationBuilder.annotationPast);
    annotations.add(AnnotationBuilder.annotationFuture);
    annotations.add(AnnotationBuilder.annotationFutureOrPresent);
    annotations.add(AnnotationBuilder.annotationPastOrPresent);
    annotations.add(AnnotationBuilder.annotationNotEmpty);
    annotations.add(AnnotationBuilder.annotationNotNull);
    annotations.add(AnnotationBuilder.annotationNull);
    annotations.add(AnnotationBuilder.annotationNegative);
    annotations.add(AnnotationBuilder.annotationPositive);
    annotations.add(AnnotationBuilder.annotationAssertFalse);
    annotations.add(AnnotationBuilder.annotationAssertTrue);
  }

  public void addType(Annotation annotation) {
    Assert.notNull("annotation", annotation);
    annotations.add(annotation);
  }

  public boolean isKnowAnnotation(String annotation) {
    boolean isSimpleAnnotation = annotations.stream().anyMatch(s -> s.name().equalsIgnoreCase(annotation));
    if (!isSimpleAnnotation) {
      isSimpleAnnotation = knowAnnotationWithValue.stream().anyMatch(s -> s.equalsIgnoreCase(annotation));
    }
    return isSimpleAnnotation;
  }

  public Optional<Annotation> getType(String annotation) {
    return annotations.stream().filter(s -> s.name().equalsIgnoreCase(annotation)).findFirst();
  }

  public Annotation convertAnnotation(DslAnnotation dslAnnotation) {
    Assert.notNull("dslAnnotation", dslAnnotation);
    if (dslAnnotation.value().isPresent()) {
      return createAnnotationWithValue(dslAnnotation.name(), dslAnnotation.value().get());
    } else {
      return createSimpleAnnotation(dslAnnotation.name());
    }
  }

  public Annotation convertAnnotation(Constraint constraint) {
    Assert.notNull("constraint", constraint);
    if (constraint.value().isPresent()) {
      return createAnnotationWithValue(constraint.name(), constraint.value().get());
    } else {
      return createSimpleAnnotation(constraint.name());
    }
  }

  private Annotation createSimpleAnnotation(String name) {
    Optional<Annotation> knowAnnotation = getType(name);
    return knowAnnotation.orElseThrow();
  }

  private Annotation createAnnotationWithValue(String name, String value) {
    Assert.field("name", name).notBlank();
    Assert.field("name", name).notNull();
    return switch (name.toLowerCase()) {
      case "min" -> AnnotationBuilder.buildMin(Optional.of(value));
      case "max" -> AnnotationBuilder.buildMax(Optional.of(value));
      case "decimalmax" -> AnnotationBuilder.buildDecimalMax(Optional.of(value));
      case "decimalmin" -> AnnotationBuilder.buildDecimalMin(Optional.of(value));
      default -> throw new AnnotationConvertionException(String.format("Annotation with value %s not recognized", name));
    };
  }

  public List<Annotation> convertAnnotation(List<DslAnnotation> dslAnnotation) {
    Assert.field("dslAnnotation", dslAnnotation).noNullElement();
    return dslAnnotation.stream().map(this::convertAnnotation).toList();
  }

  public Annotation convertFromConstrain(Constraint constraint) {
    Assert.notNull("constraint", constraint);
    return convertAnnotation(constraint);
  }
}
