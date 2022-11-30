package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.StringWithWitespacesException;

@UnitTest
class DslClassTypeTest {

  @Test
  void shouldHaveAValidDslContext() {
    assertEquals(DslClassType.CLASS, DslClassType.from("class"));
    assertEquals(DslClassType.RECORD, DslClassType.from("record"));
  }

  @Test
  void shouldThrowException() {
    assertThrows(
      MissingMandatoryValueException.class,
      () -> {
        DslClassType.from(null);
      },
      "MissingMandatoryValueException was expected"
    );
  }

  @Test
  void shouldThrowExceptionWithSpace() {
    assertThrows(
      StringWithWitespacesException.class,
      () -> {
        DslClassType.from("class ");
      },
      "StringWithWitespacesException was expected"
    );
  }
}
