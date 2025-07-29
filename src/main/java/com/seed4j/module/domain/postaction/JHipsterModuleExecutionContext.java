package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleExecutionContext(JHipsterProjectFolder projectFolder) {
  public JHipsterModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
