package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

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
