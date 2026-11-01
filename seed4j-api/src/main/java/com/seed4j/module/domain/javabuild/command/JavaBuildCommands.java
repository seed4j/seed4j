package com.seed4j.module.domain.javabuild.command;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record JavaBuildCommands(Collection<? extends JavaBuildCommand> commands) {
  public static final JavaBuildCommands EMPTY = new JavaBuildCommands(List.of());

  public JavaBuildCommands(Collection<? extends JavaBuildCommand> commands) {
    Assert.field("commands", commands).notNull().noNullElement();

    this.commands = List.copyOf(commands);
  }

  public JavaBuildCommands merge(JavaBuildCommands other) {
    Assert.notNull("other", other);

    return new JavaBuildCommands(Stream.concat(commands().stream(), other.commands().stream()).toList());
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }

  @SuppressWarnings("unchecked")
  public Collection<JavaBuildCommand> get() {
    return (Collection<JavaBuildCommand>) commands();
  }
}
