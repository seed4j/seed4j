package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleDependency(Seed4JModuleSlug module) implements Seed4JLandscapeDependency {
  public Seed4JModuleDependency {
    Assert.notNull("module", module);
  }

  @Override
  public Seed4JSlug slug() {
    return module();
  }

  @Override
  public Seed4JLandscapeElementType type() {
    return Seed4JLandscapeElementType.MODULE;
  }
}
