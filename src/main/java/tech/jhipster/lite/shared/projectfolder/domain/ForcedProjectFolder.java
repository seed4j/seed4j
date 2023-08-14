package tech.jhipster.lite.shared.projectfolder.domain;

import java.nio.file.Paths;
import java.util.UUID;
import tech.jhipster.lite.shared.error.domain.Assert;

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
    return Paths.get(prefix).resolve(UUID.randomUUID().toString()).toString();
  }
}
