package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;

public record SeedStartupCommands(Collection<? extends SeedStartupCommand> commands) {
  public SeedStartupCommands(Collection<? extends SeedStartupCommand> commands) {
    this.commands = SeedCollections.immutable(commands);
  }

  @SuppressWarnings("unchecked")
  public Collection<SeedStartupCommand> get() {
    return (Collection<SeedStartupCommand>) commands;
  }
}
