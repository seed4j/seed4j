package tech.jhipster.lite.module.infrastructure.secondary.git;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

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
