package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleToApply(Seed4JModuleSlug slug, Seed4JModuleProperties properties) {
  public Seed4JModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }
}
