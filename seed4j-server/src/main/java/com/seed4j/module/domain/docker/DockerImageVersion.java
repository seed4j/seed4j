package com.seed4j.module.domain.docker;

import com.seed4j.shared.error.domain.Assert;

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
