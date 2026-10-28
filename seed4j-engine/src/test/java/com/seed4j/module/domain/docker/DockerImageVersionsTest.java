package com.seed4j.module.domain.docker;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class DockerImageVersionsTest {

  @Test
  void shouldNotGetUnknownImage() {
    assertThatThrownBy(() -> DockerImageVersions.EMPTY.get(new DockerImageName("unknown")))
      .isExactlyInstanceOf(UnknownDockerImageException.class)
      .hasMessageContaining("unknown");
  }

  @Test
  void shouldGetKnownImage() {
    DockerImageVersion image = new DockerImageVersion("tomcat", "1.2.3");

    DockerImageVersions versions = new DockerImageVersions(List.of(image));

    assertThat(versions.get(new DockerImageName("tomcat"))).isEqualTo(image);
  }
}
