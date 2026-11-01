package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;

public record Seed4JTemplatedFiles(Collection<Seed4JTemplatedFile> files) {
  public Seed4JTemplatedFiles(Collection<Seed4JTemplatedFile> files) {
    this.files = Seed4JCollections.immutable(files);
  }

  public Collection<Seed4JTemplatedFile> get() {
    return files();
  }
}
