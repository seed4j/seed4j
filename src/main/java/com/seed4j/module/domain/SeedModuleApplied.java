package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;

public record SeedModuleApplied(SeedModuleSlug slug, SeedModuleProperties properties, Instant time) {
  public SeedModuleApplied {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
    Assert.notNull("time", time);
  }
}
