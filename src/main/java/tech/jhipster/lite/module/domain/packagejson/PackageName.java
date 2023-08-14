package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.shared.error.domain.Assert;

public record PackageName(String packageName) {
  public PackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }
}
