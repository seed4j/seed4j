package tech.jhipster.lite.module.infrastructure.secondary.docker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.docker.DockerImageName;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImageVersions;
import tech.jhipster.lite.module.domain.docker.DockerVersion;
import tech.jhipster.lite.module.domain.docker.UnknownDockerImageException;

@UnitTest
class JHipsterDockerImagesTest {

  @Test
  void shouldNotReadImageWithoutReaders() {
    JHipsterDockerImages images = new JHipsterDockerImages(List.of());

    assertThatThrownBy(() -> images.get(new DockerImageName("unknown"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldNotReadUnknownImage() {
    JHipsterDockerImages images = new JHipsterDockerImages(List.of(emptyReader()));

    assertThatThrownBy(() -> images.get(new DockerImageName("unknown"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldGetFirstKnownImage() {
    DockerImagesReader firstReader = () -> versions(version("mysql", "8.0.29"));
    DockerImagesReader secondReader = () -> versions(version("mysql", "8.0.12"));
    JHipsterDockerImages images = new JHipsterDockerImages(List.of(emptyReader(), firstReader, secondReader));

    DockerImageVersion dockerImage = images.get(new DockerImageName("MYsql"));

    assertThat(dockerImage.name()).isEqualTo(new DockerImageName("mysql"));
    assertThat(dockerImage.version()).isEqualTo(new DockerVersion("8.0.29"));
    assertThat(dockerImage.fullName()).isEqualTo("mysql:8.0.29");
  }

  private static DockerImageVersions versions(DockerImageVersion... versions) {
    return new DockerImageVersions(List.of(versions));
  }

  private static DockerImageVersion version(String slug, String version) {
    return new DockerImageVersion(slug, version);
  }

  private DockerImagesReader emptyReader() {
    return () -> DockerImageVersions.EMPTY;
  }
}
