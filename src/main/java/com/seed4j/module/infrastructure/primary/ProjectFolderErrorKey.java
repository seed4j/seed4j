package com.seed4j.module.infrastructure.primary;

import com.seed4j.shared.error.domain.ErrorKey;

enum ProjectFolderErrorKey implements ErrorKey {
  INVALID_FOLDER("invalid-project-folder");

  private final String key;

  ProjectFolderErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
