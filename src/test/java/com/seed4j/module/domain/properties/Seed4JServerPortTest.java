package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class Seed4JServerPortTest {

  @Test
  void shouldGetDefaultServerPortFromNullPort() {
    assertThat(new Seed4JServerPort(null).get()).isEqualTo(8080);
  }

  @Test
  void shouldGetServerPortFromPort() {
    assertThat(new Seed4JServerPort(9000).get()).isEqualTo(9000);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "-90000,com.seed4j.shared.error.domain.NumberValueTooLowException",
      "90000,com.seed4j.shared.error.domain.NumberValueTooHighException",
    }
  )
  void shouldValidatePortNumbers(int port, Class<Exception> exceptionClass) {
    assertThatExceptionOfType(exceptionClass).isThrownBy(() -> new Seed4JServerPort(port));
  }

  @Test
  void testToStringShowsPortNumber() {
    assertThat(new Seed4JServerPort(9000)).hasToString("9000");
  }
}
