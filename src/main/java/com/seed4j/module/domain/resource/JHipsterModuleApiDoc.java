package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleApiDoc(JHipsterModuleGroup group, JHipsterModuleOperation operation) {
  public JHipsterModuleApiDoc(String group, String operation) {
    this(new JHipsterModuleGroup(group), new JHipsterModuleOperation(operation));
  }

  public JHipsterModuleApiDoc {
    Assert.notNull("group", group);
    Assert.notNull("operation", operation);
  }
}
