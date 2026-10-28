package com.seed4j.module.domain.javabuild.command;

import com.seed4j.shared.error.domain.Assert;

public record AddGradleTasksTestInstruction(String instruction) implements JavaBuildCommand {
  public AddGradleTasksTestInstruction {
    Assert.notNull("instruction", instruction);
  }

  public String get() {
    return instruction;
  }
}
