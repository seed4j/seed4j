package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JProjectFilePath(String path) {
  public Seed4JProjectFilePath {
    Assert.notBlank("path", path);
  }

  public Seed4JProjectFilePath append(String path) {
    Assert.notBlank("path", path);

    return new Seed4JProjectFilePath(path() + "/" + path);
  }

  public String get() {
    return path();
  }
}
