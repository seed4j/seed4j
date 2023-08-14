package tech.jhipster.lite.module.domain.javabuild.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JavaBuildCommands(Collection<JavaBuildCommand> commands) {
  public static final JavaBuildCommands EMPTY = new JavaBuildCommands(List.of());

  public JavaBuildCommands(Collection<JavaBuildCommand> commands) {
    this.commands = JHipsterCollections.immutable(commands);
  }

  public JavaBuildCommands merge(JavaBuildCommands other) {
    Assert.notNull("other", other);

    List<JavaBuildCommand> mergedCommands = new ArrayList<>();
    mergedCommands.addAll(commands());
    mergedCommands.addAll(other.commands());

    return new JavaBuildCommands(mergedCommands);
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }

  public Collection<JavaBuildCommand> get() {
    return commands();
  }
}
