package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.ErrorKey;

enum ReplacementErrorKey implements ErrorKey {
  MANDATORY_REPLACEMENT_ERROR("mandatory-replacement-error"),
  UNKNOWN_CURRENT_VALUE("unknown-current-replacement-value");

  private final String key;

  ReplacementErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
