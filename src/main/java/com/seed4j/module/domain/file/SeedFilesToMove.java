package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record SeedFilesToMove(Collection<SeedFileToMove> files) {
  public SeedFilesToMove(Collection<SeedFileToMove> files) {
    this.files = SeedCollections.immutable(files);
  }

  public Stream<SeedFileToMove> stream() {
    return files().stream();
  }
}
