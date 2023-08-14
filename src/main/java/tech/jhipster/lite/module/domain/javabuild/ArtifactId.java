package tech.jhipster.lite.module.domain.javabuild;

import tech.jhipster.lite.shared.error.domain.Assert;

public record ArtifactId(String artifactId) {
  public ArtifactId {
    Assert.notBlank("artifactId", artifactId);
  }

  public String get() {
    return artifactId();
  }
}
