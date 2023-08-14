package tech.jhipster.lite.statistic.domain;

import java.util.UUID;
import tech.jhipster.lite.shared.error.domain.Assert;

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
