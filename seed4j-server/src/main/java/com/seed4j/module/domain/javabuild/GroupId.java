package com.seed4j.module.domain.javabuild;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public record GroupId(String groupId) {
  public GroupId {
    Assert.notBlank("groupId", groupId);
  }

  public String get() {
    return groupId();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return groupId();
  }
}
