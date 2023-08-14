package tech.jhipster.lite.module.domain.javabuild;

import tech.jhipster.lite.shared.error.domain.Assert;

public record GroupId(String groupId) {
  public GroupId {
    Assert.notBlank("groupId", groupId);
  }

  public String get() {
    return groupId();
  }
}
