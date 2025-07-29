package com.seed4j.module.domain.landscape;

import com.seed4j.shared.error.domain.ErrorKey;

enum LandscapeErrorKey implements ErrorKey {
  DUPLICATED_SLUG("duplicated-landscape-slug"),
  UNKNOWN_DEPENDENCY("unknown-landscape-dependency"),
  MISSING_ROOT_ELEMENT("missing-landscape-root");

  private final String key;

  LandscapeErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
