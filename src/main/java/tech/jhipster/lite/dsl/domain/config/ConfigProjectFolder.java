package tech.jhipster.lite.dsl.domain.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record ConfigProjectFolder(String folder) {
  public static final String DEFAULT_BASE_PROJECT_FOLDER = "/tmp/";

  public static final ConfigProjectFolder newConfigProjectFolder() {
    return new ConfigProjectFolder(DEFAULT_BASE_PROJECT_FOLDER + UUID.randomUUID().toString());
  }

  public ConfigProjectFolder {
    folder = buildBasePackage(folder);
  }

  private String buildBasePackage(String projectFolder) {
    if (StringUtils.isBlank(projectFolder)) {
      return DEFAULT_BASE_PROJECT_FOLDER + UUID.randomUUID().toString();
    }

    return projectFolder;
  }

  public Path filePath(String file) {
    Assert.notNull("file", file);

    return Paths.get(folder(), file);
  }

  public String get() {
    return folder();
  }
}
