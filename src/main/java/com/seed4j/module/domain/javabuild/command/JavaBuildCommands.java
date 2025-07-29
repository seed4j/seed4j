package com.seed4j.module.domain.javabuild.command;

import static com.seed4j.shared.collection.domain.JHipsterCollections.*;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record JavaBuildCommands(Collection<? extends JavaBuildCommand> commands) {
  public static final JavaBuildCommands EMPTY = new JavaBuildCommands(List.of());

  public JavaBuildCommands(Collection<? extends JavaBuildCommand> commands) {
    this.commands = immutable(commands);
  }

  public JavaBuildCommands merge(JavaBuildCommands other) {
    Assert.notNull("other", other);

    return new JavaBuildCommands(concat(commands(), other.commands()));
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }

  @SuppressWarnings("unchecked")
  public Collection<JavaBuildCommand> get() {
    return (Collection<JavaBuildCommand>) commands();
  }
}
