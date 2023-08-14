package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class InvalidProjectFolderExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidProjectFolderException exception = new InvalidProjectFolderException();

    assertThat(exception.getMessage()).isEqualTo("Project folder is not valid");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ProjectFolderErrorKey.INVALID_FOLDER);
  }
}
