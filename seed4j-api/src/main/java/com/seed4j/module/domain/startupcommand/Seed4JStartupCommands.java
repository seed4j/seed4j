package com.seed4j.module.domain.startupcommand;

import java.util.Collection;
import java.util.List;

public record Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
  public Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
    this.commands = List.copyOf(commands);
  }

  @SuppressWarnings("unchecked")
  public Collection<Seed4JStartupCommand> get() {
    return (Collection<Seed4JStartupCommand>) commands;
  }
}
