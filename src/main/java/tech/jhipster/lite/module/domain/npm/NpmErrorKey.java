package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

enum NpmErrorKey implements ErrorKey {
  UNKNOWN_PACKAGE("unknown-npm-package");

  private final String key;

  NpmErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
