package com.seed4j.module.domain.packagejson;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
  public PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
    this.dependencies = List.copyOf(dependencies);
  }

  public boolean isEmpty() {
    return dependencies().isEmpty();
  }

  public Stream<PackageJsonDependency> stream() {
    return dependencies().stream();
  }
}
