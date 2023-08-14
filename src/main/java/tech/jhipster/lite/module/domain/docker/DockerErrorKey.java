package tech.jhipster.lite.module.domain.docker;

import tech.jhipster.lite.shared.error.domain.ErrorKey;

enum DockerErrorKey implements ErrorKey {
  UNKNOWN_DOCKER_IMAGE("unknown-docker-image");

  private final String key;

  DockerErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
