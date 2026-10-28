package com.seed4j.module.domain.file;

import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.Seed4JProjectFilesPaths;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.stream.Stream;

public record Seed4JFilesToDelete(Collection<Seed4JProjectFilePath> files) {
  public Seed4JFilesToDelete(Collection<Seed4JProjectFilePath> files) {
    this.files = Seed4JCollections.immutable(files);
  }

  public Stream<Seed4JProjectFilePath> stream() {
    return files().stream();
  }

  public Seed4JFilesToDelete add(Seed4JProjectFilesPaths other) {
    Assert.notNull("other", other);

    return new Seed4JFilesToDelete(Stream.concat(files().stream(), other.stream()).toList());
  }
}
