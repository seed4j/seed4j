package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class MalformedAdditionalInformationExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    var cause = new RuntimeException();
    MalformedAdditionalInformationException exception = new MalformedAdditionalInformationException(cause);

    assertThat(exception.getMessage()).isEqualTo("Malformed XML additional elements for plugin");
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.key()).isEqualTo(MavenDependencyErrorKey.MALFORMED_ADDITIONAL_INFORMATION);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
