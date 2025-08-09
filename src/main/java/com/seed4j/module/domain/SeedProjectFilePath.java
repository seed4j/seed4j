package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;

public record SeedProjectFilePath(String path) {
  public SeedProjectFilePath {
    Assert.notBlank("path", path);
  }

  public SeedProjectFilePath append(String path) {
    Assert.notBlank("path", path);

    return new SeedProjectFilePath(path() + "/" + path);
  }

  public String get() {
    return path();
  }
}
