package com.seed4j.shared.projectfolder.domain;

import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.UUID;

public class ForcedProjectFolder implements ProjectFolder {

  private final Path prefix;

  public ForcedProjectFolder(String prefix) {
    Assert.notNull("prefix", prefix);

    this.prefix = Path.of(prefix);
  }

  @Override
  public boolean isInvalid(String folderPath) {
    Assert.notNull("folderPath", folderPath);

    Path folderAsPath = Path.of(folderPath);

    boolean isRootDir = prefix.equals(folderAsPath);
    boolean isDirectChild = prefix.equals(folderAsPath.getParent());

    return isRootDir || !isDirectChild;
  }

  @Override
  public String generatePath() {
    return prefix.resolve(UUID.randomUUID().toString()).toString();
  }
}
