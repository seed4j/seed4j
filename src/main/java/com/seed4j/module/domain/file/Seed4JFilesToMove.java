package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record Seed4JFilesToMove(Collection<Seed4JFileToMove> files) {
  public Seed4JFilesToMove(Collection<Seed4JFileToMove> files) {
    this.files = Seed4JCollections.immutable(files);
  }

  public Stream<Seed4JFileToMove> stream() {
    return files().stream();
  }
}
