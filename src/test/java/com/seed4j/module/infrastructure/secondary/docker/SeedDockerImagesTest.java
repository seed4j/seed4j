package com.seed4j.module.infrastructure.secondary.docker;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.docker.DockerImageName;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImageVersions;
import com.seed4j.module.domain.docker.DockerVersion;
import com.seed4j.module.domain.docker.UnknownDockerImageException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedDockerImagesTest {

  @Test
  void shouldNotReadImageWithoutReaders() {
    SeedDockerImages images = new SeedDockerImages(List.of());

    assertThatThrownBy(() -> images.get(new DockerImageName("unknown"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldNotReadUnknownImage() {
    SeedDockerImages images = new SeedDockerImages(List.of(emptyReader()));

    assertThatThrownBy(() -> images.get(new DockerImageName("unknown"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldGetFirstKnownImage() {
    DockerImagesReader firstReader = () -> versions(version("mysql", "8.0.29"));
    DockerImagesReader secondReader = () -> versions(version("mysql", "8.0.12"));
    SeedDockerImages images = new SeedDockerImages(List.of(emptyReader(), firstReader, secondReader));

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
