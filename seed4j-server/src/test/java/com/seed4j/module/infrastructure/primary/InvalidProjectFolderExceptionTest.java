package com.seed4j.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidProjectFolderExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidProjectFolderException exception = new InvalidProjectFolderException("/growth");

    assertThat(exception.getMessage()).isEqualTo("Project folder is not valid: /growth");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ProjectFolderErrorKey.INVALID_FOLDER);
  }
}
