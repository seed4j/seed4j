package com.seed4j.module.domain.file;

import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.shared.error.domain.Assert;

public record SeedFileToMove(SeedProjectFilePath source, SeedDestination destination) {
  public SeedFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
