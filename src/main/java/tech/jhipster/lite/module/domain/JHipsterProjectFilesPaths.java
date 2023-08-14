package tech.jhipster.lite.module.domain;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;

public record JHipsterProjectFilesPaths(Collection<JHipsterProjectFilePath> paths) {
  public JHipsterProjectFilesPaths(Collection<JHipsterProjectFilePath> paths) {
    this.paths = JHipsterCollections.immutable(paths);
  }

  public Stream<JHipsterProjectFilePath> stream() {
    return paths().stream();
  }
}
