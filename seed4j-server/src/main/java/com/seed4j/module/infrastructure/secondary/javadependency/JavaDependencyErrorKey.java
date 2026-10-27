package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.shared.error.domain.ErrorKey;

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
