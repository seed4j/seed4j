package com.seed4j.module.domain.file;

import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.JHipsterProjectFilesPaths;
import com.seed4j.shared.collection.domain.JHipsterCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.stream.Stream;

public record JHipsterFilesToDelete(Collection<JHipsterProjectFilePath> files) {
  public JHipsterFilesToDelete(Collection<JHipsterProjectFilePath> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Stream<JHipsterProjectFilePath> stream() {
    return files().stream();
  }

  public JHipsterFilesToDelete add(JHipsterProjectFilesPaths other) {
    Assert.notNull("other", other);

    return new JHipsterFilesToDelete(Stream.concat(files().stream(), other.stream()).toList());
  }
}
