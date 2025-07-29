package com.seed4j.shared.enumeration.domain;

import com.seed4j.shared.error.domain.ErrorKey;

enum EnumsErrorKey implements ErrorKey {
  UNMAPPABLE_ENUM("unmappable-enum");

  private final String key;

  EnumsErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
