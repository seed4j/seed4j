package tech.jhipster.lite.dsl.domain.clazz.field;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.domain.DslApplication;
import tech.jhipster.lite.dsl.domain.clazz.DslClassType;
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
    assertEquals("test", classField.getType().get());
  }

  @Test
  void commentCanNotBeNull() {
    ClassField classField = ClassField.fieldBuilder().name(new FieldName("test")).type(new FieldType("test")).build();
    assertNotNull(classField);
    assertNotNull(classField.getComment());
    assertTrue(classField.getComment().isEmpty());
  }
}
