package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.ErrorKey;

enum JavaDependencyErrorKey implements ErrorKey {
  UNKNOWN_VERSION("unknown-java-version");

  private final String key;

  JavaDependencyErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
