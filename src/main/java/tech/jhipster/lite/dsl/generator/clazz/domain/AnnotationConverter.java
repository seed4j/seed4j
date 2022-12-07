package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.*;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
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
      return switch (dslAnnotation.name().toLowerCase()) {
        case "min" -> AnnotationBuilder.buildMin(dslAnnotation.value());
        case "max" -> AnnotationBuilder.buildMax(dslAnnotation.value());
        case "decimalmax" -> AnnotationBuilder.buildDecimalMax(dslAnnotation.value());
        case "decimalmin" -> AnnotationBuilder.buildDecimalMin(dslAnnotation.value());
        default -> throw new AnnotationConvertionException(String.format("Annotation with value %s not recognized", dslAnnotation.name()));
      };
    } else {
      Optional<Annotation> knowAnnotation = getType(dslAnnotation.name());
      return knowAnnotation.orElseThrow();
    }
  }

  public List<Annotation> convertAnnotation(List<DslAnnotation> dslAnnotation) {
    Assert.field("dslAnnotation", dslAnnotation).noNullElement();
    return dslAnnotation.stream().map(this::convertAnnotation).toList();
  }
}
