package tech.jhipster.lite.module.domain.file;

import java.util.Collection;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;

public record JHipsterTemplatedFiles(Collection<JHipsterTemplatedFile> files) {
  public JHipsterTemplatedFiles(Collection<JHipsterTemplatedFile> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Collection<JHipsterTemplatedFile> get() {
    return files();
  }
}
