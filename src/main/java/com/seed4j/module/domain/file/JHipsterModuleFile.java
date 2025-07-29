package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;

public record JHipsterModuleFile(JHipsterFileContent content, JHipsterDestination destination, boolean executable) {
  public JHipsterModuleFile {
    Assert.notNull("content", content);
    Assert.notNull("destination", destination);
  }
}
