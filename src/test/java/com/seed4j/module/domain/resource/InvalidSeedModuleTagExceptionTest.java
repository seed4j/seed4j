package com.seed4j.module.domain.resource;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidSeedModuleTagExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidSeedModuleTagException exception = new InvalidSeedModuleTagException("invalidTag");

    assertThat(exception.getMessage()).isEqualTo(
      "The module tag \"invalidTag\" is invalid (blank, bad format, whitespace...). Tag should be only lower case letters, numbers and hyphens (-)"
    );
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.INVALID_TAG);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.parameters()).containsOnly(entry("tag", "invalidTag"));
  }
}
