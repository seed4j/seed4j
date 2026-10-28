package com.seed4j.module.domain.mavenplugin;

import com.seed4j.shared.error.domain.Assert;

public record MavenPluginExecutionId(String executionId) {
  public MavenPluginExecutionId {
    Assert.notBlank("executionId", executionId);
  }

  public String get() {
    return executionId();
  }

  @Override
  public String toString() {
    return get();
  }
}
