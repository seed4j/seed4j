package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class SeedServerPortTest {

  @Test
  void shouldGetDefaultServerPortFromNullPort() {
    assertThat(new SeedServerPort(null).get()).isEqualTo(8080);
  }

  @Test
  void shouldGetServerPortFromPort() {
    assertThat(new SeedServerPort(9000).get()).isEqualTo(9000);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "-90000,com.seed4j.shared.error.domain.NumberValueTooLowException",
      "90000,com.seed4j.shared.error.domain.NumberValueTooHighException",
    }
  )
  void shouldValidatePortNumbers(int port, Class<Exception> exceptionClass) {
    assertThatExceptionOfType(exceptionClass).isThrownBy(() -> new SeedServerPort(port));
  }

  @Test
  void testToStringShowsPortNumber() {
    assertThat(new SeedServerPort(9000)).hasToString("9000");
  }
}
