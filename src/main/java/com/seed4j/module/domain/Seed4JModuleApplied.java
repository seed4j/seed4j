package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;

public record Seed4JModuleApplied(Seed4JModuleSlug slug, Seed4JModuleProperties properties, Instant time) {
  public Seed4JModuleApplied {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
    Assert.notNull("time", time);
  }
}
