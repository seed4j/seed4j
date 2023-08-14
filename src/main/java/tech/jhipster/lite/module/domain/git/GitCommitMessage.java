package tech.jhipster.lite.module.domain.git;

import tech.jhipster.lite.shared.error.domain.Assert;

public record GitCommitMessage(String message) {
  public GitCommitMessage {
    Assert.notBlank("message", message);
  }

  public String get() {
    return message();
  }
}
