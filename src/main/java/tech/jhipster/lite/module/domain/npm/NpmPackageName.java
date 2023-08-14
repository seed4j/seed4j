package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.shared.error.domain.Assert;

public record NpmPackageName(String packageName) {
  public NpmPackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }
}
