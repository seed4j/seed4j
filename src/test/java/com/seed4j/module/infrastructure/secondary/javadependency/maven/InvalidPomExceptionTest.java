package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidPomExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidPomException exception = new InvalidPomException();

    assertThat(exception.getMessage()).isEqualTo(
      "Your pom.xml file is invalid, you have to define, at least, an artifact id for your project"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(MavenDependencyErrorKey.INVALID_POM);
  }
}
