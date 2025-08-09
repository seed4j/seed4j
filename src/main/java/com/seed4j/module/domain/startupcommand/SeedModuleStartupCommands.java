package com.seed4j.module.domain.startupcommand;

import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class SeedModuleStartupCommands {

  private final SeedStartupCommands commands;

  private SeedModuleStartupCommands(SeedModuleStartupCommandsBuilder builder) {
    commands = new SeedStartupCommands(builder.commands);
  }

  public static SeedModuleStartupCommandsBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleStartupCommandsBuilder(module);
  }

  public SeedStartupCommands commands() {
    return commands;
  }

  public static final class SeedModuleStartupCommandsBuilder {

    private final SeedModuleBuilder module;
    private final Collection<SeedStartupCommand> commands = new ArrayList<>();

    private SeedModuleStartupCommandsBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleStartupCommandsBuilder dockerCompose(DockerComposeFile dockerComposeFile) {
      commands.add(new DockerComposeStartupCommandLine(dockerComposeFile));
      return this;
    }

    public SeedModuleStartupCommandsBuilder dockerCompose(String dockerComposeFile) {
      return dockerCompose(new DockerComposeFile(dockerComposeFile));
    }

    public SeedModuleStartupCommandsBuilder maven(String commandLineParameters) {
      commands.add(new MavenStartupCommandLine(commandLineParameters));
      return this;
    }

    public SeedModuleStartupCommandsBuilder gradle(String commandLineParameters) {
      commands.add(new GradleStartupCommandLine(commandLineParameters));
      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleStartupCommands build() {
      return new SeedModuleStartupCommands(this);
    }
  }
}
