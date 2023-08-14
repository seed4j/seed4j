package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.shared.error.domain.Assert;

public record NpmPackage(NpmPackageName name, NpmPackageVersion version) {
  public NpmPackage(String name, String version) {
    this(new NpmPackageName(name), new NpmPackageVersion(version));
  }

  public NpmPackage {
    Assert.notNull("name", name);
    Assert.notNull("version", version);
  }
}
