package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleApiDoc(Seed4JModuleGroup group, Seed4JModuleOperation operation) {
  public Seed4JModuleApiDoc(String group, String operation) {
    this(new Seed4JModuleGroup(group), new Seed4JModuleOperation(operation));
  }

  public Seed4JModuleApiDoc {
    Assert.notNull("group", group);
    Assert.notNull("operation", operation);
  }
}
