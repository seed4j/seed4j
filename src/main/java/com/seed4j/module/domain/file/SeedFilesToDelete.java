package com.seed4j.module.domain.file;

import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.SeedProjectFilesPaths;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.stream.Stream;

public record SeedFilesToDelete(Collection<SeedProjectFilePath> files) {
  public SeedFilesToDelete(Collection<SeedProjectFilePath> files) {
    this.files = SeedCollections.immutable(files);
  }

  public Stream<SeedProjectFilePath> stream() {
    return files().stream();
  }

  public SeedFilesToDelete add(SeedProjectFilesPaths other) {
    Assert.notNull("other", other);

    return new SeedFilesToDelete(Stream.concat(files().stream(), other.stream()).toList());
  }
}
