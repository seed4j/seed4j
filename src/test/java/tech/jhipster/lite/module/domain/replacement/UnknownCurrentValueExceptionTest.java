package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownCurrentValueExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownCurrentValueException exception = new UnknownCurrentValueException("value", "content");

    assertThat(exception.getMessage()).isEqualTo("Can't find \"value\" in content");
    assertThat(exception.key()).isEqualTo(ReplacementErrorKey.UNKNOWN_CURRENT_VALUE);
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
  }
}
