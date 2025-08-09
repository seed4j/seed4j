package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleToApply(SeedModuleSlug slug, JHipsterModuleProperties properties) {
  public JHipsterModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }
}
