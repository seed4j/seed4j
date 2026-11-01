package com.seed4j.module.domain.javabuild;

import com.seed4j.shared.error.domain.Assert;

public record ArtifactId(String artifactId) {
  public ArtifactId {
    Assert.notBlank("artifactId", artifactId);
  }

  public String get() {
    return artifactId();
  }

  @Override
  public String toString() {
    return artifactId();
  }
}
