package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterProjectFilePath(String path) {
  public JHipsterProjectFilePath {
    Assert.notBlank("path", path);
  }

  public JHipsterProjectFilePath append(String path) {
    Assert.notBlank("path", path);

    return new JHipsterProjectFilePath(path() + "/" + path);
  }

  public String get() {
    return path();
  }
}
