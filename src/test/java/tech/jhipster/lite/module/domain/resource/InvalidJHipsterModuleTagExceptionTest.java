package tech.jhipster.lite.module.domain.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class InvalidJHipsterModuleTagExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidJHipsterModuleTagException exception = new InvalidJHipsterModuleTagException("invalidTag");

    assertThat(exception.getMessage()).isEqualTo(
      "The module tag \"invalidTag\" is invalid (blank, bad format, whitespace...). Tag should be only lower case letters, numbers and hyphens (-)"
    );
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.INVALID_TAG);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.parameters()).containsOnly(entry("tag", "invalidTag"));
  }
}
