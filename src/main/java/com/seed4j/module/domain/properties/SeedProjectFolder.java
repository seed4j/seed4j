package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Files;
import java.nio.file.Path;

public record SeedProjectFolder(String folder) {
  public SeedProjectFolder {
    Assert.notBlank("folder", folder);
  }

  public Path filePath(String file) {
    Assert.notNull("file", file);

    return Path.of(folder(), file);
  }

  public boolean fileExists(String fileName) {
    return Files.exists(filePath(fileName));
  }

  public String get() {
    return folder();
  }
}
