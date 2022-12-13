package tech.jhipster.lite.dsl.common.domain.clazz.enums;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.StringWithWitespacesException;

@UnitTest
class EnumNameTest {

  @Test
  void shouldThrowExceptionWhenNotValid() {
    assertThrows(
      StringWithWitespacesException.class,
      () -> {
        new EnumName("class with space");
      },
      "StringWithWitespacesException was expected"
    );
  }
}
