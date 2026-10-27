package com.seed4j.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

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
