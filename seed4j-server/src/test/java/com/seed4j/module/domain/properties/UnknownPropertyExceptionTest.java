package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class UnknownPropertyExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownPropertyException exception = new UnknownPropertyException("unknown");

    assertThat(exception.getMessage()).isEqualTo("Unknown property unknown");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(PropertiesErrorKey.UNKNOWN_PROPERTY);
    assertThat(exception.parameters()).containsOnly(entry("key", "unknown"));
  }
}
