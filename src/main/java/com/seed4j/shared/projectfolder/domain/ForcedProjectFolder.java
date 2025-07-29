package com.seed4j.shared.projectfolder.domain;

import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.UUID;

public class ForcedProjectFolder implements ProjectFolder {

  private final String prefix;

  public ForcedProjectFolder(String prefix) {
    Assert.notNull("prefix", prefix);

    this.prefix = prefix;
  }

  @Override
  public boolean isInvalid(String folderPath) {
    Assert.notNull("folderPath", folderPath);

    return !folderPath.startsWith(prefix) || folderPath.contains("..");
  }

  @Override
  public String generatePath() {
    return Path.of(prefix).resolve(UUID.randomUUID().toString()).toString();
  }
}
