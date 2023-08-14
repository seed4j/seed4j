package tech.jhipster.lite.module.domain.javadependency;

import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JavaDependencyVersion(VersionSlug slug, Version version) {
  public JavaDependencyVersion(String slug, String version) {
    this(new VersionSlug(slug), new Version(version));
  }

  public JavaDependencyVersion {
    Assert.notNull("slug", slug);
    Assert.notNull("version", version);
  }
}
