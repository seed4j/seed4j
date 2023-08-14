package tech.jhipster.lite.project.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownProjectExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownProjectException exception = new UnknownProjectException();

    assertThat(exception.getMessage()).isEqualTo("A user tried to download an unknown project");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ProjectErrorKey.UNKOWN_PROJECT);
  }
}
