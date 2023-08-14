package tech.jhipster.lite.shared.error.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class UnauthorizedValueExceptionTest {

  public static final String FIELD = "field";

  @Test
  void shouldGetExceptionInformation() {
    UnauthorizedValueException exception = new UnauthorizedValueException(FIELD);
    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value");
  }

  @Test
  void shouldGetExceptionForBlankValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forNegativeValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value (negative)");
  }

  @Test
  void shouldGetExceptionForInconsistentValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forInconsistentValue(FIELD, 132);

    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value (inconsistent : 132)");
  }

  @Test
  void shouldGetExceptionForInconsistentValues() {
    UnauthorizedValueException exception = UnauthorizedValueException.forInconsistentValues("field1", 1, "field2", 2);

    assertThat(exception.getMessage())
      .isEqualTo("The field \"field2\" was given an unauthorized value (2 which is inconsistent with \"field1\" : 1)");
  }

  @Test
  void shouldGetExceptionForUnknownValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forUnknownValue(FIELD);
    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value (unknown)");
  }

  @Test
  void shouldGetExceptionForUnequalValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forUnequalValues("field1", "field2", 2, 3);
    assertThat(exception.getMessage())
      .isEqualTo("The field \"field2\" was given an unauthorized value (3 not equal with field \"field1\" : 2)");
  }

  @Test
  void shouldGetExceptionForUnexpectedType() {
    UnauthorizedValueException exception = UnauthorizedValueException.forUnexpectedType(FIELD);
    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value (unexpected type)");
  }

  @Test
  void shouldGetExceptionForTooShortValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forTooShortValue(FIELD);
    assertThat(exception.getMessage()).isEqualTo("The field \"field\" was given an unauthorized value (too short)");
  }

  @Test
  void shouldGetExceptionForUnexpectedValue() {
    UnauthorizedValueException exception = UnauthorizedValueException.forUnexpectedValue(FIELD, "Simpson", "Soprano");
    assertThat(exception.getMessage())
      .isEqualTo("The field \"field\" was given an unauthorized value (expected : \"Soprano\", found : \"Simpson\")");
  }
}
