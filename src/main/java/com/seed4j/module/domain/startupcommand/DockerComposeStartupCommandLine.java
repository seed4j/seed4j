package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;

public record DockerComposeStartupCommandLine(DockerComposeFile dockerComposeFile) implements JHipsterStartupCommand {
  public DockerComposeStartupCommandLine {
    Assert.notNull("dockerComposeFile", dockerComposeFile);
  }

  @Override
  public StartupCommandLine commandLine() {
    return new StartupCommandLine("docker compose -f %s up -d".formatted(dockerComposeFile.path()));
  }
}
