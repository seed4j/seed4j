package tech.jhipster.lite.project.domain;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

enum ProjectErrorKey implements ErrorKey {
  UNKOWN_PROJECT("unknown-project-folder");

  private final String key;

  ProjectErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
