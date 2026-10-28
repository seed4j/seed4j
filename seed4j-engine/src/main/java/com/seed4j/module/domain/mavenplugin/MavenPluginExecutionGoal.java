package com.seed4j.module.domain.mavenplugin;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record MavenPluginExecutionGoal(String goal) {
  public MavenPluginExecutionGoal {
    Assert.notBlank("goal", goal);
  }

  public String get() {
    return goal();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return get();
  }
}
