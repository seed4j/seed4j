package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record JHipsterModulesToApply(Collection<SeedModuleSlug> slugs, SeedModuleProperties properties) {
  public JHipsterModulesToApply {
    Assert.notEmpty("slugs", slugs);
    Assert.notNull("properties", properties);
  }
}
