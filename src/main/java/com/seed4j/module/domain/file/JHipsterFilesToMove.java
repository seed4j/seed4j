package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record JHipsterFilesToMove(Collection<JHipsterFileToMove> files) {
  public JHipsterFilesToMove(Collection<JHipsterFileToMove> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Stream<JHipsterFileToMove> stream() {
    return files().stream();
  }
}
