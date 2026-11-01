package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.ErrorKey;

enum NodeErrorKey implements ErrorKey {
  UNKNOWN_PACKAGE("unknown-node-package");

  private final String key;

  NodeErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
