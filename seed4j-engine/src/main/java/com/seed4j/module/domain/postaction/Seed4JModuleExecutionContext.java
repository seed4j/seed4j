package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleExecutionContext(Seed4JProjectFolder projectFolder) {
  public Seed4JModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
