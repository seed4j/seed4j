package com.seed4j.project.domain.download;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidDownloadExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidDownloadException exception = new InvalidDownloadException();

    assertThat(exception.getMessage()).isEqualTo("A user tried to download a project from a protected folder");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(DownloadErrorKey.INVALID_DOWNLOAD);
  }
}
