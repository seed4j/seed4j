package tech.jhipster.lite.dsl.common.domain.git;

import tech.jhipster.lite.error.domain.Assert;

public record GitCommitMessage(String message) {
  public GitCommitMessage {
    Assert.notBlank("message", message);
  }

  public String get() {
    return message();
  }
}
