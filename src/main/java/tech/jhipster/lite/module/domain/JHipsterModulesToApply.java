package tech.jhipster.lite.module.domain;

import java.util.Collection;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModulesToApply(Collection<JHipsterModuleSlug> slugs, JHipsterModuleProperties properties) {
  public JHipsterModulesToApply {
    Assert.notEmpty("slugs", slugs);
    Assert.notNull("properties", properties);
  }
}
