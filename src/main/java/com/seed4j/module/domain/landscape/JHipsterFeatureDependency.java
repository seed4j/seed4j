package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterFeatureSlug;
import com.seed4j.module.domain.JHipsterSlug;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterFeatureDependency(JHipsterFeatureSlug feature) implements JHipsterLandscapeDependency {
  public JHipsterFeatureDependency {
    Assert.notNull("feature", feature);
  }

  @Override
  public JHipsterSlug slug() {
    return feature();
  }

  @Override
  public JHipsterLandscapeElementType type() {
    return JHipsterLandscapeElementType.FEATURE;
  }
}
