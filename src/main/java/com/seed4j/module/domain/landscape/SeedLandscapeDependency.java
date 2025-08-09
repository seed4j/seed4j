package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedSlug;

public sealed interface SeedLandscapeDependency permits SeedFeatureDependency, SeedModuleDependency {
  SeedSlug slug();

  SeedLandscapeElementType type();
}
