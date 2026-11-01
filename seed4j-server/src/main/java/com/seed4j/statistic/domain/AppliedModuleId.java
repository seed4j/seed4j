package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.UUID;

public record AppliedModuleId(UUID id) {
  public AppliedModuleId {
    Assert.notNull("id", id);
  }

  public static AppliedModuleId newId() {
    return new AppliedModuleId(UUID.randomUUID());
  }

  public UUID get() {
    return id();
  }
}
