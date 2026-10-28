package com.seed4j.module.domain.docker;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

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
