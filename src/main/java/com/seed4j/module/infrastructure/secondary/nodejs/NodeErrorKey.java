package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.shared.error.domain.ErrorKey;

enum NodeErrorKey implements ErrorKey {
  INSTALL_ERROR("install-error");

  private final String key;

  NodeErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
