package com.seed4j.module.infrastructure.secondary.docker;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImageVersions;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Repository
@Order
class FileSystemDockerImagesReader implements DockerImagesReader {

  private static final String DOCKER_FROM = "from ";

  private final ProjectFiles files;

  public FileSystemDockerImagesReader(ProjectFiles files) {
    this.files = files;
  }

  @Override
  public DockerImageVersions get() {
    Collection<DockerImageVersion> versionsRead = Stream.of(files.readString("/generator/dependencies/Dockerfile").split("[\r\n]"))
      .map(String::trim)
      .map(String::toLowerCase)
      .map(toDockerImage())
      .flatMap(Optional::stream)
      .toList();

    return new DockerImageVersions(versionsRead);
  }

  private Function<String, Optional<DockerImageVersion>> toDockerImage() {
    return line -> {
      int versionSeparatorIndex = line.lastIndexOf(':');

      if (versionSeparatorIndex == -1) {
        return Optional.empty();
      }

      return Optional.of(
        new DockerImageVersion(line.substring(DOCKER_FROM.length(), versionSeparatorIndex), line.substring(versionSeparatorIndex + 1))
      );
    };
  }
}
