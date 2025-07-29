package com.seed4j.module.infrastructure.secondary.docker;

import com.seed4j.module.domain.docker.DockerImageVersions;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.shared.memoizer.domain.Memoizers;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;

@Repository
class JHipsterDockerImages implements DockerImages {

  private final Supplier<DockerImageVersions> versions;

  public JHipsterDockerImages(Collection<DockerImagesReader> readers) {
    versions = Memoizers.of(versionsReader(readers));
  }

  private Supplier<DockerImageVersions> versionsReader(Collection<DockerImagesReader> readers) {
    return () -> readers.stream().map(DockerImagesReader::get).reduce(DockerImageVersions.EMPTY, DockerImageVersions::merge);
  }

  @Override
  public DockerImageVersions get() {
    return versions.get();
  }
}
