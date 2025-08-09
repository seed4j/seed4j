package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;

public record GradleStartupCommandLine(String commandLineParameters) implements SeedStartupCommand {
  public GradleStartupCommandLine {
    Assert.notNull("commandLineParameters", commandLineParameters);
  }

  @Override
  public StartupCommandLine commandLine() {
    return new StartupCommandLine("./gradlew " + commandLineParameters.trim());
  }
}
