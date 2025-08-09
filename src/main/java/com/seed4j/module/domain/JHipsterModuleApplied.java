package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;

public record JHipsterModuleApplied(SeedModuleSlug slug, SeedModuleProperties properties, Instant time) {
  public JHipsterModuleApplied {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
    Assert.notNull("time", time);
  }
}
