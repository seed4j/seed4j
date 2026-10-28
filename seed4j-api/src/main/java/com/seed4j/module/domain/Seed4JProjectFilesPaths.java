package com.seed4j.module.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
  public Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
    this.paths = List.copyOf(paths);
  }

  public Stream<Seed4JProjectFilePath> stream() {
    return paths().stream();
  }
}
