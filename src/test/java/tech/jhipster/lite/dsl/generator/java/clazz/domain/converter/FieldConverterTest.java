package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.field.FieldType;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.field.FieldTypeImpl;

@UnitTest
class FieldConverterTest {

  @Test
  void shouldAddNewAnnotationManaged() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    fieldConverter.addType(new FieldTypeImpl("MyNewAnnotation", Optional.of(new ClassImport("my.import", false))));
    FieldType field = fieldConverter.getType("MyNewAnnotation");
    assertEquals("MyNewAnnotation", field.name());
  }

  @Test
  void shouldReturnNotKnow() {
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    assertThatThrownBy(() -> fieldConverter.getType("MyNewAnnotation"))
      .isExactlyInstanceOf(NoSuchElementException.class)
      .hasMessageContaining("No value present");
  }
}
