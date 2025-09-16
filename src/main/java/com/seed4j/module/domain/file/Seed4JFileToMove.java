package com.seed4j.module.domain.file;

import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JFileToMove(Seed4JProjectFilePath source, Seed4JDestination destination) {
  public Seed4JFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
