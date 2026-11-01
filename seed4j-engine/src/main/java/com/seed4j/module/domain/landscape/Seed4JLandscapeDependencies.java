package com.seed4j.module.domain.landscape;

import static java.util.function.Predicate.not;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

public record Seed4JLandscapeDependencies(Collection<? extends Seed4JLandscapeDependency> dependencies) {
  public Seed4JLandscapeDependencies {
    Assert.notEmpty("dependencies", dependencies);
  }

  @SuppressWarnings("NullAway") // Dataflow analysis limitation
  public static Optional<Seed4JLandscapeDependencies> of(@Nullable Collection<? extends Seed4JLandscapeDependency> dependencies) {
    return Optional.ofNullable(dependencies).filter(not(Collection::isEmpty)).map(Seed4JLandscapeDependencies::new);
  }

  public long count() {
    return dependencies().size();
  }

  public Stream<Seed4JLandscapeDependency> stream() {
    return dependencies().stream().map(Seed4JLandscapeDependency.class::cast);
  }
}
