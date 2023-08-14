package tech.jhipster.lite.shared.enumeration.domain;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

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
