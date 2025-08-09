package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public record SeedModuleToApply(SeedModuleSlug slug, SeedModuleProperties properties) {
  public SeedModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }
}
