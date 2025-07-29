package com.seed4j.module.domain.git;

import com.seed4j.shared.error.domain.Assert;

public record GitCommitMessage(String message) {
  public GitCommitMessage {
    Assert.notBlank("message", message);
  }

  public String get() {
    return message();
  }
}
