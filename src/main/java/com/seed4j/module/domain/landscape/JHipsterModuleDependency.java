package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterModuleSlug;
import com.seed4j.module.domain.JHipsterSlug;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleDependency(JHipsterModuleSlug module) implements JHipsterLandscapeDependency {
  public JHipsterModuleDependency {
    Assert.notNull("module", module);
  }

  @Override
  public JHipsterSlug slug() {
    return module();
  }

  @Override
  public JHipsterLandscapeElementType type() {
    return JHipsterLandscapeElementType.MODULE;
  }
}
