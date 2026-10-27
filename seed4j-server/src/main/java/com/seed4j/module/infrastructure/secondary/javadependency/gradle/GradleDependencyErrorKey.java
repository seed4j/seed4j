package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import com.seed4j.shared.error.domain.ErrorKey;

enum GradleDependencyErrorKey implements ErrorKey {
  INVALID_TOML_VERSION_CATALOG("invalid-toml-version-catalog-file"),
  MISSING_PROFILE("missing-gradle-profile");

  private final String key;

  GradleDependencyErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
