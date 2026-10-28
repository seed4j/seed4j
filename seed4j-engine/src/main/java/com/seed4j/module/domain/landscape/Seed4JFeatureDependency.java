package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JFeatureDependency(Seed4JFeatureSlug feature) implements Seed4JLandscapeDependency {
  public Seed4JFeatureDependency {
    Assert.notNull("feature", feature);
  }

  @Override
  public Seed4JSlug slug() {
    return feature();
  }

  @Override
  public Seed4JLandscapeElementType type() {
    return Seed4JLandscapeElementType.FEATURE;
  }
}
