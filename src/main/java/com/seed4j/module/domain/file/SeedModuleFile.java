package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;

public record SeedModuleFile(SeedFileContent content, SeedDestination destination, boolean executable) {
  public SeedModuleFile {
    Assert.notNull("content", content);
    Assert.notNull("destination", destination);
  }
}
