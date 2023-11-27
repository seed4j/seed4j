package tech.jhipster.lite.module.domain.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class DuplicatedSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    DuplicatedSlugException exception = new DuplicatedSlugException();

    assertThat(exception.getMessage()).isEqualTo("Found a duplicated module slug, ensure that slugs are unique");
    assertThat(exception.key()).isEqualTo(ResourceErrorKey.DUPLICATED_SLUG);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
