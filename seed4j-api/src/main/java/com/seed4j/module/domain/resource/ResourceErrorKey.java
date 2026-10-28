package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.ErrorKey;

enum ResourceErrorKey implements ErrorKey {
  DUPLICATED_SLUG("duplicated-slug"),
  INVALID_TAG("invalid-tag"),
  UNKNOWN_SLUG("unknown-slug");

  private final String key;

  ResourceErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
