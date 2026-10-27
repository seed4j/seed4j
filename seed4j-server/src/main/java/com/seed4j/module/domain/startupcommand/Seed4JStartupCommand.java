package com.seed4j.module.domain.startupcommand;

public sealed interface Seed4JStartupCommand permits DockerComposeStartupCommandLine, GradleStartupCommandLine, MavenStartupCommandLine {
  StartupCommandLine commandLine();
}
