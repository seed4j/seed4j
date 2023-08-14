package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.shared.error.domain.Assert;

public record PackageJsonDependency(PackageName packageName, VersionSource versionSource) {
  public PackageJsonDependency {
    Assert.notNull("packageName", packageName);
    Assert.notNull("versionSource", versionSource);
  }
}
