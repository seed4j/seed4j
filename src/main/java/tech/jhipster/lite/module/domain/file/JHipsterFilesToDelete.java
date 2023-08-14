package tech.jhipster.lite.module.domain.file;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.JHipsterProjectFilesPaths;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

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
