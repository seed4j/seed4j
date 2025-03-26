package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class InvalidProjectFolderExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidProjectFolderException exception = new InvalidProjectFolderException("/chips");

    assertThat(exception.getMessage()).isEqualTo("Project folder is not valid: /chips");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ProjectFolderErrorKey.INVALID_FOLDER);
  }
}
