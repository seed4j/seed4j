package com.seed4j.project.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class UnknownProjectExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownProjectException exception = new UnknownProjectException();

    assertThat(exception.getMessage()).isEqualTo("A user tried to download an unknown project");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ProjectErrorKey.UNKNOWN_PROJECT);
  }
}
