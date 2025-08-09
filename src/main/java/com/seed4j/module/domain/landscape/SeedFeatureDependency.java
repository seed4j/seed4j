package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.shared.error.domain.Assert;

public record SeedFeatureDependency(SeedFeatureSlug feature) implements SeedLandscapeDependency {
  public SeedFeatureDependency {
    Assert.notNull("feature", feature);
  }

  @Override
  public SeedSlug slug() {
    return feature();
  }

  @Override
  public SeedLandscapeElementType type() {
    return SeedLandscapeElementType.FEATURE;
  }
}
