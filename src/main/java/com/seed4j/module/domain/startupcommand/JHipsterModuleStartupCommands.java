package com.seed4j.module.domain.startupcommand;

import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class JHipsterModuleStartupCommands {

  private final JHipsterStartupCommands commands;

  private JHipsterModuleStartupCommands(JHipsterModuleStartupCommandsBuilder builder) {
    commands = new JHipsterStartupCommands(builder.commands);
  }

  public static JHipsterModuleStartupCommandsBuilder builder(SeedModuleBuilder module) {
    return new JHipsterModuleStartupCommandsBuilder(module);
  }

  public JHipsterStartupCommands commands() {
    return commands;
  }

  public static final class JHipsterModuleStartupCommandsBuilder {

    private final SeedModuleBuilder module;
    private final Collection<JHipsterStartupCommand> commands = new ArrayList<>();

    private JHipsterModuleStartupCommandsBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleStartupCommandsBuilder dockerCompose(DockerComposeFile dockerComposeFile) {
      commands.add(new DockerComposeStartupCommandLine(dockerComposeFile));
      return this;
    }

    public JHipsterModuleStartupCommandsBuilder dockerCompose(String dockerComposeFile) {
      return dockerCompose(new DockerComposeFile(dockerComposeFile));
    }

    public JHipsterModuleStartupCommandsBuilder maven(String commandLineParameters) {
      commands.add(new MavenStartupCommandLine(commandLineParameters));
      return this;
    }

    public JHipsterModuleStartupCommandsBuilder gradle(String commandLineParameters) {
      commands.add(new GradleStartupCommandLine(commandLineParameters));
      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public JHipsterModuleStartupCommands build() {
      return new JHipsterModuleStartupCommands(this);
    }
  }
}
