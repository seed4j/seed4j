package com.seed4j.module.domain.gradleconfiguration;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddGradleConfiguration;
import com.seed4j.module.domain.javabuild.command.AddGradleTasksTestInstruction;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JModuleGradleConfigurations {

  private final Collection<GradleConfiguration> configurations;
  private final Collection<GradleTasksTestInstruction> tasksTestInstructions;

  public Seed4JModuleGradleConfigurations(Seed4JModuleGradleConfigurationBuilder builder) {
    Assert.notNull("configurations", builder.configurations);
    Assert.notNull("tasksTestInstructions", builder.tasksTestInstructions);

    this.configurations = builder.configurations;
    this.tasksTestInstructions = builder.tasksTestInstructions;
  }

  public static Seed4JModuleGradleConfigurationBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleGradleConfigurationBuilder(module);
  }

  public JavaBuildCommands buildChanges() {
    return Stream.of(configurationToAddGradleConfiguration(), tasksTestInstructionsToAddTasksTestInstruction())
      .flatMap(Function.identity())
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
  }

  private Stream<JavaBuildCommands> configurationToAddGradleConfiguration() {
    return Stream.of(
      new JavaBuildCommands(
        configurations
          .stream()
          .map(configuration -> new AddGradleConfiguration(configuration.get()))
          .toList()
      )
    );
  }

  private Stream<JavaBuildCommands> tasksTestInstructionsToAddTasksTestInstruction() {
    return Stream.of(
      new JavaBuildCommands(
        tasksTestInstructions
          .stream()
          .map(tasksTestInstruction -> new AddGradleTasksTestInstruction(tasksTestInstruction.get()))
          .toList()
      )
    );
  }

  public static final class Seed4JModuleGradleConfigurationBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<GradleConfiguration> configurations = new ArrayList<>();
    private final Collection<GradleTasksTestInstruction> tasksTestInstructions = new ArrayList<>();

    private Seed4JModuleGradleConfigurationBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleGradleConfigurationBuilder addConfiguration(String configuration) {
      configurations.add(new GradleConfiguration(configuration));

      return this;
    }

    public Seed4JModuleGradleConfigurationBuilder addTasksTestInstruction(String instruction) {
      tasksTestInstructions.add(new GradleTasksTestInstruction(instruction));

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleGradleConfigurations build() {
      return new Seed4JModuleGradleConfigurations(this);
    }
  }
}
