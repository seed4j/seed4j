package tech.jhipster.lite.module.domain.docker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownDockerImageExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownDockerImageException exception = new UnknownDockerImageException(new DockerImageName("image name"));

    assertThat(exception.getMessage()).isEqualTo(
      "Can't find image image name, forgot to add it to src/main/resources/generator/dependencies/Dockerfile?"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(DockerErrorKey.UNKNOWN_DOCKER_IMAGE);
    assertThat(exception.parameters()).containsOnly(entry("imageName", "image name"));
  }
}
