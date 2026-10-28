package com.seed4j.shared.projectfolder.domain;

import java.nio.file.Path;
import java.util.UUID;
import org.apache.commons.lang3.SystemUtils;

public class FreeProjectFolder implements ProjectFolder {

  @Override
  public boolean isInvalid(String folderPath) {
    return false;
  }

  @Override
  public String generatePath() {
    return Path.of(SystemUtils.JAVA_IO_TMPDIR).resolve(UUID.randomUUID().toString()).toString();
  }
}
