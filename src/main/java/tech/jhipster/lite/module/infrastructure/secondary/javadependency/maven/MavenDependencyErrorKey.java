package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import tech.jhipster.lite.error.domain.ErrorKey;

enum MavenDependencyErrorKey implements ErrorKey {
  MALFORMED_ADDITIONAL_INFORMATION("malformed-java-dependency-additional-information"),
  INVALID_POM("invalid-pom-file");

  private final String key;

  MavenDependencyErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
