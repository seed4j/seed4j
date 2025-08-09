package com.seed4j.module.domain.landscape;

import static java.util.function.Predicate.*;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public record SeedLandscapeDependencies(Collection<? extends SeedLandscapeDependency> dependencies) {
  public SeedLandscapeDependencies {
    Assert.notEmpty("dependencies", dependencies);
  }

  public static Optional<SeedLandscapeDependencies> of(Collection<? extends SeedLandscapeDependency> dependencies) {
    return Optional.ofNullable(dependencies).filter(not(Collection::isEmpty)).map(SeedLandscapeDependencies::new);
  }

  public long count() {
    return dependencies().size();
  }

  public Stream<SeedLandscapeDependency> stream() {
    return dependencies().stream().map(SeedLandscapeDependency.class::cast);
  }
}
