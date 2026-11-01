package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;

public record Statistics(long appliedModules) {
  public Statistics {
    Assert.field("appliedModules", appliedModules).min(0);
  }
}
