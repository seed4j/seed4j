package tech.jhipster.lite.module.domain.landscape;

import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;
import tech.jhipster.lite.module.domain.JHipsterSlug;
import tech.jhipster.lite.shared.error.domain.Assert;

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
