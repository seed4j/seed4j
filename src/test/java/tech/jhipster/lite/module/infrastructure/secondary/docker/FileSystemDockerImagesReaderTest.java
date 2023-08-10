package tech.jhipster.lite.module.infrastructure.secondary.docker;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.docker.DockerImageName;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerVersion;
import tech.jhipster.lite.module.domain.docker.UnknownDockerImageException;

@UnitTest
class FileSystemDockerImagesReaderTest {

  @Test
  void shouldIgnoreInvalidVersionInFile() {
    FileSystemDockerImagesReader reader = mockedReader("""
      FROM invalid
       FROM mysql:8.0.29
      FROM postgres:14.3""");

    assertThatThrownBy(() -> reader.get().get(new DockerImageName("invalid"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldGetVersionsFromFile() {
    FileSystemDockerImagesReader reader = mockedReader("""
      FROM mongo:5.0.9
       FROM mysql:8.0.29
      FROM postgres:14.3
      """);

    DockerImageVersion image = reader.get().get(new DockerImageName("mysql"));

    assertThat(image.name()).isEqualTo(new DockerImageName("mysql"));
    assertThat(image.version()).isEqualTo(new DockerVersion("8.0.29"));
    assertThat(image.fullName()).isEqualTo("mysql:8.0.29");
  }

  private FileSystemDockerImagesReader mockedReader(String content) {
    ProjectFiles files = mock(ProjectFiles.class);

    when(files.readString("/generator/dependencies/Dockerfile")).thenReturn(content);

    return new FileSystemDockerImagesReader(files);
  }
}
