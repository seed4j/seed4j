package tech.jhipster.lite.module.domain.postaction;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleExecutionContext(JHipsterProjectFolder projectFolder) {
  public JHipsterModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
