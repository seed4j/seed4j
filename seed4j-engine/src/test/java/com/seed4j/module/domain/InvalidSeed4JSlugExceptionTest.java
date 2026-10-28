package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidSeed4JSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidSeed4JSlugException exception = new InvalidSeed4JSlugException("invalid slug");

    assertThat(exception.getMessage()).isEqualTo(
      "The slug \"invalid slug\" is invalid (blank, bad format, ...). Slug should be only lower case letters, numbers and hyphens (-)"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(ModuleErrorKey.INVALID_SLUG);
    assertThat(exception.parameters()).containsOnly(entry("slug", "invalid slug"));
  }
}
