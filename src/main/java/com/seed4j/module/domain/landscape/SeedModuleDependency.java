package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.shared.error.domain.Assert;

public record SeedModuleDependency(SeedModuleSlug module) implements SeedLandscapeDependency {
  public SeedModuleDependency {
    Assert.notNull("module", module);
  }

  @Override
  public SeedSlug slug() {
    return module();
  }

  @Override
  public SeedLandscapeElementType type() {
    return SeedLandscapeElementType.MODULE;
  }
}
