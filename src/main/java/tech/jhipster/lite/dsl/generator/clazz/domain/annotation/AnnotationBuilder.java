package tech.jhipster.lite.dsl.generator.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigFluentMethodBuilder;
import tech.jhipster.lite.error.domain.Assert;

public class AnnotationBuilder {

  private AnnotationBuilder() {}

  public static final Annotation annotationPast = new AnnotationSimpleImpl(
    "Past",
    Optional.of(new ClassImport("jakarta.validation.constraints.Past", false))
  );
  public static final Annotation annotationFuture = new AnnotationSimpleImpl(
    "Future",
    Optional.of(new ClassImport("jakarta.validation.constraints.Future", false))
  );
  public static final Annotation annotationFutureOrPresent = new AnnotationSimpleImpl(
    "FutureOrPresent",
    Optional.of(new ClassImport("jakarta.validation.constraints.FutureOrPresent", false))
  );
  public static final Annotation annotationPastOrPresent = new AnnotationSimpleImpl(
    "PastOrPresent",
    Optional.of(new ClassImport("jakarta.validation.constraints.PastOrPresent", false))
  );
  public static final Annotation annotationNotEmpty = new AnnotationSimpleImpl(
    "NotEmpty",
    Optional.of(new ClassImport("jakarta.validation.constraints.NotEmpty", false))
  );
  public static final Annotation annotationNotNull = new AnnotationSimpleImpl(
    "NotNull",
    Optional.of(new ClassImport("jakarta.validation.constraints.NotNull", false))
  );
  public static final Annotation annotationNull = new AnnotationSimpleImpl(
    "Null",
    Optional.of(new ClassImport("jakarta.validation.constraints.Null", false))
  );
  public static final Annotation annotationNegative = new AnnotationSimpleImpl(
    "Negative",
    Optional.of(new ClassImport("jakarta.validation.constraints.Negative", false))
  );
  public static final Annotation annotationPositive = new AnnotationSimpleImpl(
    "Positive",
    Optional.of(new ClassImport("jakarta.validation.constraints.Positive", false))
  );
  public static final Annotation annotationAssertFalse = new AnnotationSimpleImpl(
    "AssertFalse",
    Optional.of(new ClassImport("jakarta.validation.constraints.AssertFalse", false))
  );
  public static final Annotation annotationAssertTrue = new AnnotationSimpleImpl(
    "AssertTrue",
    Optional.of(new ClassImport("jakarta.validation.constraints.AssertTrue", false))
  );

  public static ConfigFluentMethodBuilder.Builder builderFluentMethod() {
    return new ConfigFluentMethodBuilder.Builder();
  }

  public static Annotation buildMax(Optional<String> optMax) {
    Assert.notNull("optMax", optMax);
    return new AnnotationWithValueImpl("Max", optMax, Optional.of(new ClassImport("jakarta.validation.constraints.Max", false)));
  }

  public static Annotation buildDecimalMax(Optional<String> optMax) {
    Assert.notNull("optMax", optMax);
    return new AnnotationWithValueImpl(
      "DecimalMax",
      optMax,
      Optional.of(new ClassImport("jakarta.validation.constraints.DecimalMax", false))
    );
  }

  public static Annotation buildMin(Optional<String> optMin) {
    Assert.notNull("optMin", optMin);
    return new AnnotationWithValueImpl("Min", optMin, Optional.of(new ClassImport("jakarta.validation.constraints.Min", false)));
  }

  public static Annotation buildDecimalMin(Optional<String> optMin) {
    Assert.notNull("optMin", optMin);
    return new AnnotationWithValueImpl(
      "DecimalMin",
      optMin,
      Optional.of(new ClassImport("jakarta.validation.constraints.DecimalMin", false))
    );
  }

  public static Annotation buildSize(Optional<String> optMin, Optional<String> optMax) {
    Assert.notNull("optMin", optMin);
    Assert.notNull("optMax", optMax);
    return new AnnotationWithDoubleValueImpl(
      "Size",
      optMin,
      optMax,
      Optional.of(new ClassImport("jakarta.validation.constraints.Size", false))
    );
  }
}
