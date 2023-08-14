package tech.jhipster.lite.module.domain;

import java.time.Instant;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleApplied(JHipsterModuleSlug slug, JHipsterModuleProperties properties, Instant time) {
  public JHipsterModuleApplied {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
    Assert.notNull("time", time);
  }
}
