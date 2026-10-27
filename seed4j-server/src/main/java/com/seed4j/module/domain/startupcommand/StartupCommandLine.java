package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;

public record StartupCommandLine(String commandLine) {
  public StartupCommandLine(String commandLine) {
    Assert.notBlank("commandLine", commandLine);
    this.commandLine = commandLine.trim();
  }

  public String get() {
    return commandLine();
  }
}
