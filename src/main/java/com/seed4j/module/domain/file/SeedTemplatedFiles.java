package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;

public record SeedTemplatedFiles(Collection<SeedTemplatedFile> files) {
  public SeedTemplatedFiles(Collection<SeedTemplatedFile> files) {
    this.files = SeedCollections.immutable(files);
  }

  public Collection<SeedTemplatedFile> get() {
    return files();
  }
}
