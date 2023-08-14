package tech.jhipster.lite.module.domain.docker;

import tech.jhipster.lite.shared.error.domain.Assert;

public record DockerImageName(String imageName) {
  public DockerImageName(String imageName) {
    Assert.notBlank("imageName", imageName);

    this.imageName = imageName.toLowerCase();
  }

  public String get() {
    return imageName();
  }
}
