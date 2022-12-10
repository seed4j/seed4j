package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.AnnotationBuilder;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.AnnotationConvertionException;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.Constraint;
import tech.jhipster.lite.error.domain.Assert;

public class AnnotationConverter {

  private final List<Annotation> annotations = new LinkedList<>();

  private static final String DSL_ANNOTATION_PACKAGE = "package";
  private static final String DSL_ANNOTATION_BUILDER = "builder";
  private final List<String> DSL_ANNOTATIONS = Stream.of(DSL_ANNOTATION_PACKAGE, DSL_ANNOTATION_BUILDER).toList();

  private final List<String> knowAnnotationWithValue = Stream.of("min", "max", "size", "decimalMin", "decimalMax").toList();

  public AnnotationConverter() {
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

  public boolean isAnnotationUseByDsl(DslAnnotation dslAnnotation) {
    Assert.notNull("dslAnnotation", dslAnnotation);
    return DSL_ANNOTATIONS.stream().anyMatch(annot -> annot.equalsIgnoreCase(dslAnnotation.name()));
  }

  public ClassPackage getPackageAnnotation(List<DslAnnotation> dslAnnotations) {
    Assert.field("dslAnnotations", dslAnnotations).notNull().noNullElement();

    Optional<DslAnnotation> optDslAnnotation = dslAnnotations
      .stream()
      .filter(dslAnnotation -> DSL_ANNOTATION_PACKAGE.equalsIgnoreCase(dslAnnotation.name()))
      .findFirst();
    AtomicReference<ClassPackage> result = new AtomicReference<>(ClassPackage.EMPTY);
    optDslAnnotation.flatMap(DslAnnotation::value).ifPresent(valuePackage -> result.set(new ClassPackage(valuePackage)));
    return result.get();
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
    Assert.field("key", name).notBlank();
    Assert.field("key", name).notNull();
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
