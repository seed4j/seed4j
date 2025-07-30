package com.seed4j.module.domain.docker;

import com.seed4j.shared.error.domain.Assert;

public record DockerVersion(String version) {
  public DockerVersion {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }
}
