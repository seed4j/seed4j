package com.seed4j.module.domain;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record JHipsterProjectFilesPaths(Collection<SeedProjectFilePath> paths) {
  public JHipsterProjectFilesPaths(Collection<SeedProjectFilePath> paths) {
    this.paths = SeedCollections.immutable(paths);
  }

  public Stream<SeedProjectFilePath> stream() {
    return paths().stream();
  }
}
