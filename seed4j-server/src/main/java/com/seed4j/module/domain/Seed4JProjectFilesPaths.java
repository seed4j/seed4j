package com.seed4j.module.domain;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
  public Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
    this.paths = Seed4JCollections.immutable(paths);
  }

  public Stream<Seed4JProjectFilePath> stream() {
    return paths().stream();
  }
}
