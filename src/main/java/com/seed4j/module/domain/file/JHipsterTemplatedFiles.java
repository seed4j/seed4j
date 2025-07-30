package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import java.util.Collection;

public record JHipsterTemplatedFiles(Collection<JHipsterTemplatedFile> files) {
  public JHipsterTemplatedFiles(Collection<JHipsterTemplatedFile> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Collection<JHipsterTemplatedFile> get() {
    return files();
  }
}
