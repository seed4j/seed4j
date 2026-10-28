package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.shared.error.domain.ErrorKey;

enum GitErrorKey implements ErrorKey {
  COMMIT_ERROR("git-commit-error"),
  INIT_ERROR("git-init-error");

  private final String key;

  GitErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
