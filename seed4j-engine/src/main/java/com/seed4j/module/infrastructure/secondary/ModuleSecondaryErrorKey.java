package com.seed4j.module.infrastructure.secondary;

import com.seed4j.shared.error.domain.ErrorKey;

enum ModuleSecondaryErrorKey implements ErrorKey {
  MISSING_PACKAGE_JSON("missing-package-json"),
  UNKNOWN_FILE_TO_DELETE("unknown-file-to-delete"),
  UNKNOWN_FILE_TO_MOVE("unknown-file-to-move");

  private final String key;

  ModuleSecondaryErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
