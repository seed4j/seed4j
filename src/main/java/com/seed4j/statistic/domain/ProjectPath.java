package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;

public record ProjectPath(String path) {
  public ProjectPath {
    Assert.notBlank("path", path);
  }

  public String get() {
    return path();
  }
}
