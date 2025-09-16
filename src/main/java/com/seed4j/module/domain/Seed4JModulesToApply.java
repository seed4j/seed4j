package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record Seed4JModulesToApply(Collection<Seed4JModuleSlug> slugs, Seed4JModuleProperties properties) {
  public Seed4JModulesToApply {
    Assert.notEmpty("slugs", slugs);
    Assert.notNull("properties", properties);
  }
}
