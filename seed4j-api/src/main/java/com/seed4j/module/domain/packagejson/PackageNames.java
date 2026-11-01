package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record PackageNames(Collection<PackageName> packageNames) {
  public PackageNames(Collection<PackageName> packageNames) {
    Assert.field("packageNames", packageNames).notNull().noNullElement();

    this.packageNames = List.copyOf(packageNames);
  }

  public boolean isEmpty() {
    return packageNames().isEmpty();
  }

  public Stream<PackageName> stream() {
    return packageNames().stream();
  }
}
