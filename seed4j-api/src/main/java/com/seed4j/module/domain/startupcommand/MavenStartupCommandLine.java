package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;

public record MavenStartupCommandLine(String commandLineParameters) implements Seed4JStartupCommand {
  public MavenStartupCommandLine {
    Assert.notNull("commandLineParameters", commandLineParameters);
  }

  @Override
  public StartupCommandLine commandLine() {
    return new StartupCommandLine("./mvnw " + commandLineParameters.trim());
  }
}
