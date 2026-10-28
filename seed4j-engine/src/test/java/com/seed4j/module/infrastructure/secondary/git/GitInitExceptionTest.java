package com.seed4j.module.infrastructure.secondary.git;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class GitInitExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    var cause = new RuntimeException();
    GitInitException exception = new GitInitException("This is an error", cause);

    assertThat(exception.getMessage()).isEqualTo("This is an error");
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.key()).isEqualTo(GitErrorKey.INIT_ERROR);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
