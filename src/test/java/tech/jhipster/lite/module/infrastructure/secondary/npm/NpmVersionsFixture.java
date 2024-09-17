package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.shared.error.domain.Assert;

public final class NpmVersionsFixture {

  private NpmVersionsFixture() {}

  public static NpmVersions npmVersions(ProjectFiles filesReader, Collection<NpmVersionsReader> customReaders) {
    Assert.notNull("customReaders", customReaders);

    return new JHipsterNpmVersions(
      Stream.concat(customReaders.stream(), Stream.of(new JHipsterLiteFileSystemNpmVersionReader(filesReader))).toList()
    );
  }
}
