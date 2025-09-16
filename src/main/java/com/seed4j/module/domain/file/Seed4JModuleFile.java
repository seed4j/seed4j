package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JModuleFile(Seed4JFileContent content, Seed4JDestination destination, boolean executable) {
  public Seed4JModuleFile {
    Assert.notNull("content", content);
    Assert.notNull("destination", destination);
  }
}
