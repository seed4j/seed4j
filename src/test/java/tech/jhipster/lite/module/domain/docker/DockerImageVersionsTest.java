package tech.jhipster.lite.module.domain.docker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

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
