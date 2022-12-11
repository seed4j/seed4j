package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.AnnotationConvertionException;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.AnnotationSimple;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ConstraintSimple;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ConstraintWithValue;

@UnitTest
class AnnotationConverterTest {

  @Test
  void shouldAddNewAnnotationManaged() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    annotationConverter.addType(new AnnotationSimple("MyNewAnnotation", Optional.of(new ClassImport("my.import", false))));
    Optional<Annotation> annot = annotationConverter.getType("MyNewAnnotation");
    assertTrue(annot.isPresent());
  }

  @Test
  void shouldReturnNotKnow() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    Optional<Annotation> annot = annotationConverter.getType("MyNewAnnotation");
    assertFalse(annot.isPresent());
  }

  @Test
  void shouldThrowException() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    Optional<Annotation> annot = annotationConverter.getType("MyNewAnnotation");
    DslAnnotation dslAnnot = new DslAnnotation("unknown", Optional.of("hdh"));
    assertThatThrownBy(() -> annotationConverter.convertAnnotation(dslAnnot))
      .isExactlyInstanceOf(AnnotationConvertionException.class)
      .hasMessageContaining("Annotation with value 'unknown' not recognized");
  }

  @Test
  void shouldConvertConstraintNotNull() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    Annotation annot = annotationConverter.convertConstraint(new ConstraintSimple("notNull"));

    assertEquals("NotNull", annot.name());
  }

  @Test
  void shouldConvertConstraintDecimalMin() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    Annotation annot = annotationConverter.convertConstraint(new ConstraintWithValue("decimalmin", Optional.of("10")));

    assertEquals("DecimalMin", annot.name());
  }

  @Test
  void shouldConvertConstraintDecimalMax() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    Annotation annot = annotationConverter.convertConstraint(new ConstraintWithValue("decimalmax", Optional.of("100")));

    assertEquals("DecimalMax", annot.name());
  }
}
