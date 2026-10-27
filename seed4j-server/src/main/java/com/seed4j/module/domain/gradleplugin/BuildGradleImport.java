package com.seed4j.module.domain.gradleplugin;

import com.seed4j.shared.error.domain.Assert;

public record BuildGradleImport(String gradleImport) {
  public BuildGradleImport {
    Assert.notBlank("gradleImport", gradleImport);
  }

  public String get() {
    return gradleImport();
  }
}
