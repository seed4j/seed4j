package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidProjectBaseNameExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidProjectBaseNameException exception = new InvalidProjectBaseNameException();

    assertThat(exception.getMessage()).isEqualTo(
      "Project names can't have special characters, only letters (no accents) and numbers allowed"
    );
    assertThat(exception.key()).isEqualTo(PropertiesErrorKey.INVALID_BASE_NAME);
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
  }
}
