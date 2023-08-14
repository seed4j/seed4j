package tech.jhipster.lite.module.domain.docker;

import tech.jhipster.lite.shared.error.domain.Assert;

public record DockerVersion(String version) {
  public DockerVersion {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }
}
