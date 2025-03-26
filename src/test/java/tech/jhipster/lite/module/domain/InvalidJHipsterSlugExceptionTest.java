package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class InvalidJHipsterSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidJHipsterSlugException exception = new InvalidJHipsterSlugException("invalid slug");

    assertThat(exception.getMessage()).isEqualTo(
      "The slug \"invalid slug\" is invalid (blank, bad format, ...). Slug should be only lower case letters, numbers and hyphens (-)"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(ModuleErrorKey.INVALID_SLUG);
    assertThat(exception.parameters()).containsOnly(entry("slug", "invalid slug"));
  }
}
