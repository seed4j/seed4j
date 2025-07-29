package com.seed4j.module.domain.file;

import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterFileToMove(JHipsterProjectFilePath source, JHipsterDestination destination) {
  public JHipsterFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
