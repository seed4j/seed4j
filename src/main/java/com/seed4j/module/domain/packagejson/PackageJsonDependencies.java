package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
  public PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
    this.dependencies = SeedCollections.immutable(dependencies);
  }

  public boolean isEmpty() {
    return dependencies().isEmpty();
  }

  public Stream<PackageJsonDependency> stream() {
    return dependencies().stream();
  }
}
