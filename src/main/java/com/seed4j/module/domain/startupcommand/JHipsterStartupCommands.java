package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;

public record JHipsterStartupCommands(Collection<? extends JHipsterStartupCommand> commands) {
  public JHipsterStartupCommands(Collection<? extends JHipsterStartupCommand> commands) {
    this.commands = SeedCollections.immutable(commands);
  }

  @SuppressWarnings("unchecked")
  public Collection<JHipsterStartupCommand> get() {
    return (Collection<JHipsterStartupCommand>) commands;
  }
}
