package com.seed4j.module.domain.gradleconfiguration;

import com.seed4j.shared.error.domain.Assert;

record GradleTasksTestInstruction(String instruction) {
  public GradleTasksTestInstruction {
    Assert.notBlank("instruction", instruction);
  }

  public String get() {
    return instruction();
  }
}
