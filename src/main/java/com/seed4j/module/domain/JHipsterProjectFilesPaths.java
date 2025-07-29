package com.seed4j.module.domain;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record JHipsterProjectFilesPaths(Collection<JHipsterProjectFilePath> paths) {
  public JHipsterProjectFilesPaths(Collection<JHipsterProjectFilePath> paths) {
    this.paths = JHipsterCollections.immutable(paths);
  }

  public Stream<JHipsterProjectFilePath> stream() {
    return paths().stream();
  }
}
