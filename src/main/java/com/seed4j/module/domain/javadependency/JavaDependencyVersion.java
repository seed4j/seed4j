package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;

public record JavaDependencyVersion(VersionSlug slug, Version version) {
  public JavaDependencyVersion(String slug, String version) {
    this(new VersionSlug(slug), new Version(version));
  }

  public JavaDependencyVersion {
    Assert.notNull("slug", slug);
    Assert.notNull("version", version);
  }
}
