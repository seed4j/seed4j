package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleToApply(JHipsterModuleSlug slug, JHipsterModuleProperties properties) {
  public JHipsterModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }
}
