package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;

public record DockerComposeFile(String path) {
  public DockerComposeFile {
    Assert.notBlank("path", path);
  }
}
