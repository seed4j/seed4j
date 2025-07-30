package com.seed4j.module.domain.mavenplugin;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record MavenPluginExecutionId(String executionId) {
  public MavenPluginExecutionId {
    Assert.notBlank("executionId", executionId);
  }

  public String get() {
    return executionId();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return get();
  }
}
