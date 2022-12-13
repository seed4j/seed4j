package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import static org.junit.jupiter.api.Assertions.*;

import javax.lang.model.element.Modifier;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

@UnitTest
class ClassFieldTest {

  @Test
  void shouldNotBuildIfNoNameAndType() {
    assertThrows(MissingMandatoryValueException.class, ClassField.fieldBuilder()::build, "MissingMandatoryValueException was expected");

    assertThrows(
      MissingMandatoryValueException.class,
      () -> {
        ClassField.fieldBuilder().name(new FieldName("test")).build();
      },
      "Type not defined"
    );

    assertThrows(
      MissingMandatoryValueException.class,
      () -> {
        ClassField.fieldBuilder().type(new FieldType("test")).build();
      },
      "Name not defined"
    );
  }

  @Test
  void shouldBuildIfNameAndType() {
    ClassField classField = ClassField.fieldBuilder().name(new FieldName("test")).type(new FieldType("test")).build();
    assertNotNull(classField);
    assertEquals("test", classField.getName().get());
    assertEquals("Test", classField.getType().get());
  }

  @Test
  void commentCanNotBeNull() {
    ClassField classField = ClassField.fieldBuilder().name(new FieldName("test")).type(new FieldType("test")).build();
    assertNotNull(classField);
    assertNotNull(classField.getComment());
    assertTrue(classField.getComment().isEmpty());
  }

  @Test
  void mustCreateFieldWithModifier() {
    ClassField classField = ClassField
      .fieldBuilder()
      .name(new FieldName("test"))
      .type(new FieldType("test"))
      .addModifiers(Modifier.ABSTRACT)
      .addModifiers(Modifier.FINAL)
      .addModifiers(Modifier.PUBLIC)
      .build();
    assertNotNull(classField);
    assertEquals(3, classField.getModifiers().size());
  }
}
