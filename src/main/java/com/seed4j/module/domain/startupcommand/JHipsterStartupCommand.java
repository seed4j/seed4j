package com.seed4j.module.domain.startupcommand;

public sealed interface JHipsterStartupCommand permits DockerComposeStartupCommandLine, GradleStartupCommandLine, MavenStartupCommandLine {
  StartupCommandLine commandLine();
}
