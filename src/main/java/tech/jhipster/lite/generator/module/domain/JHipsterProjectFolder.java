package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import tech.jhipster.lite.error.domain.Assert;

public record JHipsterProjectFolder(String folder) {
  public JHipsterProjectFolder {
    Assert.notBlank("folder", folder);
  }

  public Path filePath(String file) {
    Assert.notNull("file", file);

    return Paths.get(folder(), file);
  }
}
