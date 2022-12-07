package tech.jhipster.lite.dsl.generator.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigFluentMethodBuilder;
import tech.jhipster.lite.error.domain.Assert;

public class AnnotationBuilder {

  private AnnotationBuilder() {}

  public static final Annotation annotationPast = new AnnotationSimple(
    "Past",
    Optional.of(new ClassImport("jakarta.validation.constraints.Past", false))
  );
  public static final Annotation annotationFuture = new AnnotationSimple(
    "Future",
    Optional.of(new ClassImport("jakarta.validation.constraints.Future", false))
  );
  public static final Annotation annotationFutureOrPresent = new AnnotationSimple(
    "FutureOrPresent",
    Optional.of(new ClassImport("jakarta.validation.constraints.FutureOrPresent", false))
  );
  public static final Annotation annotationPastOrPresent = new AnnotationSimple(
    "PastOrPresent",
    Optional.of(new ClassImport("jakarta.validation.constraints.PastOrPresent", false))
  );
  public static final Annotation annotationNotEmpty = new AnnotationSimple(
    "NotEmpty",
    Optional.of(new ClassImport("jakarta.validation.constraints.NotEmpty", false))
  );
  public static final Annotation annotationNotNull = new AnnotationSimple(
    "NotNull",
    Optional.of(new ClassImport("jakarta.validation.constraints.NotNull", false))
  );
  public static final Annotation annotationNull = new AnnotationSimple(
    "Null",
    Optional.of(new ClassImport("jakarta.validation.constraints.Null", false))
  );
  public static final Annotation annotationNegative = new AnnotationSimple(
    "Negative",
    Optional.of(new ClassImport("jakarta.validation.constraints.Negative", false))
  );
  public static final Annotation annotationPositive = new AnnotationSimple(
    "Positive",
    Optional.of(new ClassImport("jakarta.validation.constraints.Positive", false))
  );
  public static final Annotation annotationAssertFalse = new AnnotationSimple(
    "AssertFalse",
    Optional.of(new ClassImport("jakarta.validation.constraints.AssertFalse", false))
  );
  public static final Annotation annotationAssertTrue = new AnnotationSimple(
    "AssertTrue",
    Optional.of(new ClassImport("jakarta.validation.constraints.AssertTrue", false))
  );

  public static ConfigFluentMethodBuilder.Builder builderFluentMethod() {
    return new ConfigFluentMethodBuilder.Builder();
  }

  public static Annotation buildMax(Optional<String> optMax) {
    Assert.notNull("optMax", optMax);
    return new AnnotationWithValue("Max", optMax, Optional.of(new ClassImport("jakarta.validation.constraints.Max", false)));
  }

  public static Annotation buildDecimalMax(Optional<String> optMax) {
    Assert.notNull("optMax", optMax);
    return new AnnotationWithValue("DecimalMax", optMax, Optional.of(new ClassImport("jakarta.validation.constraints.DecimalMax", false)));
  }

  public static Annotation buildMin(Optional<String> optMin) {
    Assert.notNull("optMin", optMin);
    return new AnnotationWithValue("Min", optMin, Optional.of(new ClassImport("jakarta.validation.constraints.Min", false)));
  }

  public static Annotation buildDecimalMin(Optional<String> optMin) {
    Assert.notNull("optMin", optMin);
    return new AnnotationWithValue("DecimalMin", optMin, Optional.of(new ClassImport("jakarta.validation.constraints.DecimalMin", false)));
  }

  public static Annotation buildSize(Optional<String> optMin, Optional<String> optMax) {
    Assert.notNull("optMin", optMin);
    Assert.notNull("optMax", optMax);
    return new AnnotationWithDoubleValue(
      "Size",
      optMin,
      optMax,
      Optional.of(new ClassImport("jakarta.validation.constraints.Size", false))
    );
  }
}
