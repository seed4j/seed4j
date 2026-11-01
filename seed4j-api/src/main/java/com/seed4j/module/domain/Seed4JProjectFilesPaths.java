package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
  public Seed4JProjectFilesPaths(Collection<Seed4JProjectFilePath> paths) {
    Assert.field("paths", paths).notNull().noNullElement();

    this.paths = List.copyOf(paths);
  }

  public Stream<Seed4JProjectFilePath> stream() {
    return paths().stream();
  }
}
