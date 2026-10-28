package com.seed4j.module.domain.startupcommand;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class Seed4JModuleStartupCommands {

  private final Seed4JStartupCommands commands;

  private Seed4JModuleStartupCommands(Seed4JModuleStartupCommandsBuilder builder) {
    commands = new Seed4JStartupCommands(builder.commands);
  }

  public static Seed4JModuleStartupCommandsBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleStartupCommandsBuilder(module);
  }

  public Seed4JStartupCommands commands() {
    return commands;
  }

  public static final class Seed4JModuleStartupCommandsBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<Seed4JStartupCommand> commands = new ArrayList<>();

    private Seed4JModuleStartupCommandsBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleStartupCommandsBuilder dockerCompose(DockerComposeFile dockerComposeFile) {
      commands.add(new DockerComposeStartupCommandLine(dockerComposeFile));
      return this;
    }

    public Seed4JModuleStartupCommandsBuilder dockerCompose(String dockerComposeFile) {
      return dockerCompose(new DockerComposeFile(dockerComposeFile));
    }

    public Seed4JModuleStartupCommandsBuilder maven(String commandLineParameters) {
      commands.add(new MavenStartupCommandLine(commandLineParameters));
      return this;
    }

    public Seed4JModuleStartupCommandsBuilder gradle(String commandLineParameters) {
      commands.add(new GradleStartupCommandLine(commandLineParameters));
      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleStartupCommands build() {
      return new Seed4JModuleStartupCommands(this);
    }
  }
}
