package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record PackageNames(Collection<PackageName> packageNames) {
  public PackageNames(Collection<PackageName> packageNames) {
    this.packageNames = JHipsterCollections.immutable(packageNames);
  }

  public boolean isEmpty() {
    return packageNames().isEmpty();
  }

  public Stream<PackageName> stream() {
    return packageNames().stream();
  }
}
