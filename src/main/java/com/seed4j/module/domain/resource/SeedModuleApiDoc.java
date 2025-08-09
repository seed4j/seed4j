package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;

public record SeedModuleApiDoc(SeedModuleGroup group, SeedModuleOperation operation) {
  public SeedModuleApiDoc(String group, String operation) {
    this(new SeedModuleGroup(group), new SeedModuleOperation(operation));
  }

  public SeedModuleApiDoc {
    Assert.notNull("group", group);
    Assert.notNull("operation", operation);
  }
}
