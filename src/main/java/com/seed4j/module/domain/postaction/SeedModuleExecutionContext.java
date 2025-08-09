package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.Assert;

public record SeedModuleExecutionContext(SeedProjectFolder projectFolder) {
  public SeedModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
