package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;

public record Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
  public Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
    this.commands = Seed4JCollections.immutable(commands);
  }

  @SuppressWarnings("unchecked")
  public Collection<Seed4JStartupCommand> get() {
    return (Collection<Seed4JStartupCommand>) commands;
  }
}
