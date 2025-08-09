package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record JHipsterModulesToApply(Collection<SeedModuleSlug> slugs, JHipsterModuleProperties properties) {
  public JHipsterModulesToApply {
    Assert.notEmpty("slugs", slugs);
    Assert.notNull("properties", properties);
  }
}
