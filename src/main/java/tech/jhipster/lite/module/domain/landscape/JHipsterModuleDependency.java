package tech.jhipster.lite.module.domain.landscape;

import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterSlug;
import tech.jhipster.lite.shared.error.domain.Assert;

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
