package com.seed4j.module.domain.docker;

import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public record DockerImageName(String imageName) {
  public DockerImageName(String imageName) {
    Assert.notBlank("imageName", imageName);

    this.imageName = imageName.toLowerCase(Locale.ROOT);
  }

  public String get() {
    return imageName();
  }
}
