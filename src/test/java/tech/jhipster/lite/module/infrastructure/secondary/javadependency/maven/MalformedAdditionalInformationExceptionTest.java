package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.ErrorStatus;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.MalformedAdditionalInformationException;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.MavenDependencyErrorKey;

@UnitTest
class MalformedAdditionalInformationExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    RuntimeException cause = new RuntimeException();
    MalformedAdditionalInformationException exception = new MalformedAdditionalInformationException(cause);

    assertThat(exception.getMessage()).isEqualTo("Malformed XML additional elements for plugin");
    assertThat(exception.getCause()).isEqualTo(cause);
    assertThat(exception.key()).isEqualTo(MavenDependencyErrorKey.MALFORMED_ADDITIONAL_INFORMATION);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
  }
}
