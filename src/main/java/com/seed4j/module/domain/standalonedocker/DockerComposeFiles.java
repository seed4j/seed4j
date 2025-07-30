package com.seed4j.module.domain.standalonedocker;

import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record DockerComposeFiles(Collection<DockerComposeFile> files) {
  public DockerComposeFiles {
    Assert.field("files", files).notNull().noNullElement();
  }

  public Collection<DockerComposeFile> get() {
    return files();
  }
}
