package com.seed4j.module.domain.startupcommand;

public sealed interface SeedStartupCommand permits DockerComposeStartupCommandLine, GradleStartupCommandLine, MavenStartupCommandLine {
  StartupCommandLine commandLine();
}
