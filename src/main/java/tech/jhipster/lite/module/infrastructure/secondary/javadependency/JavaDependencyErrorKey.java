package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

enum JavaDependencyErrorKey implements ErrorKey {
  MISSING_BUILD_CONFIGURATION("missing-java-build-configuration-files");

  private final String key;

  JavaDependencyErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
