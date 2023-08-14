package tech.jhipster.lite.module.domain.file;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;

public record JHipsterFilesToMove(Collection<JHipsterFileToMove> files) {
  public JHipsterFilesToMove(Collection<JHipsterFileToMove> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Stream<JHipsterFileToMove> stream() {
    return files().stream();
  }
}
