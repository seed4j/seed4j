package tech.jhipster.lite.module.domain.docker;

import tech.jhipster.lite.shared.error.domain.Assert;

public record DockerImageVersion(DockerImageName name, DockerVersion version) {
  public DockerImageVersion(String name, String version) {
    this(new DockerImageName(name), new DockerVersion(version));
  }

  public DockerImageVersion {
    Assert.notNull("name", name);
    Assert.notNull("version", version);
  }

  public String fullName() {
    return name().get() + ":" + version().get();
  }
}
