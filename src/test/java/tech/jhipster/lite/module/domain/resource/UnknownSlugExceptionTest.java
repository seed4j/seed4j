package tech.jhipster.lite.module.domain.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownSlugException exception = new UnknownSlugException(new JHipsterModuleSlug("unknown-slug"));

    assertThat(exception.getMessage()).isEqualTo("Module unknown-slug does not exist");
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.UNKNOWN_SLUG);
    assertThat(exception.parameters()).containsOnly(entry("slug", "unknown-slug"));
  }
}
