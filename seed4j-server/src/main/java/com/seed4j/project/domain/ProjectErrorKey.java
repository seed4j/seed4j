package com.seed4j.project.domain;

import com.seed4j.shared.error.domain.ErrorKey;

enum ProjectErrorKey implements ErrorKey {
  UNKNOWN_PROJECT("unknown-project-folder");

  private final String key;

  ProjectErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
