package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record PackageName(String packageName) {
  public PackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return packageName;
  }
}
