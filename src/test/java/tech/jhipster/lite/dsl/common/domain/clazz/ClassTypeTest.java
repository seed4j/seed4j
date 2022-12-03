package tech.jhipster.lite.dsl.common.domain.clazz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.StringWithWitespacesException;

@UnitTest
class ClassTypeTest {

  @Test
  void shouldHaveAValidDslContext() {
    assertEquals(ClassType.CLASS, ClassType.from("class"));
    assertEquals(ClassType.RECORD, ClassType.from("record"));
  }

  @Test
  void shouldThrowException() {
    assertThrows(
      MissingMandatoryValueException.class,
      () -> {
        ClassType.from(null);
      },
      "MissingMandatoryValueException was expected"
    );
  }

  @Test
  void shouldThrowExceptionWithSpace() {
    assertThrows(
      StringWithWitespacesException.class,
      () -> {
        ClassType.from("class ");
      },
      "StringWithWitespacesException was expected"
    );
  }
}
