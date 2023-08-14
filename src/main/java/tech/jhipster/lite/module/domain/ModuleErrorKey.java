package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

enum ModuleErrorKey implements ErrorKey {
  INVALID_SLUG("invalid-slug");

  private final String key;

  ModuleErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
