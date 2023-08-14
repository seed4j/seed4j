package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterModuleApiDoc(JHipsterModuleGroup group, JHipsterModuleOperation operation) {
  public JHipsterModuleApiDoc(String group, String operation) {
    this(new JHipsterModuleGroup(group), new JHipsterModuleOperation(operation));
  }

  public JHipsterModuleApiDoc {
    Assert.notNull("group", group);
    Assert.notNull("operation", operation);
  }
}
