package com.seed4j.project.infrastructure.secondary;

import com.seed4j.shared.error.domain.ErrorKey;

enum ProjectErrorKey implements ErrorKey {
  FORMATTING_ERROR("project-formatting-error"),
  ZIPPING_ERROR("project-zipping-error");

  private final String key;

  ProjectErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
