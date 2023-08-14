package tech.jhipster.lite.shared.enumeration.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;
import tech.jhipster.lite.shared.error.domain.StandardErrorKey;

@UnitTest
class UnmappableEnumExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnmappableEnumException exception = new UnmappableEnumException(StandardErrorKey.class, ErrorStatus.class);

    assertThat(exception.getMessage())
      .isEqualTo(
        "Can't map class tech.jhipster.lite.shared.error.domain.StandardErrorKey to class tech.jhipster.lite.shared.error.domain.ErrorStatus"
      );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(EnumsErrorKey.UNMAPPABLE_ENUM);
  }
}
