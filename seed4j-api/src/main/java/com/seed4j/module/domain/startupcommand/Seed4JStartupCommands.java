package com.seed4j.module.domain.startupcommand;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
  public Seed4JStartupCommands(Collection<? extends Seed4JStartupCommand> commands) {
    Assert.field("commands", commands).notNull().noNullElement();

    this.commands = List.copyOf(commands);
  }

  @SuppressWarnings("unchecked")
  public Collection<Seed4JStartupCommand> get() {
    return (Collection<Seed4JStartupCommand>) commands;
  }
}
