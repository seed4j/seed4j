package com.seed4j.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class MandatoryReplacementExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    var cause = new RuntimeException();
    MandatoryReplacementException exception = new MandatoryReplacementException(cause);

    assertThat(exception.getMessage()).isEqualTo("Error applying mandatory replacement");
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.key()).isEqualTo(ReplacementErrorKey.MANDATORY_REPLACEMENT_ERROR);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
