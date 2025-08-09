package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.stream.Stream;

public final class NpmVersionsFixture {

  private NpmVersionsFixture() {}

  public static NodeVersions npmVersions(ProjectFiles filesReader, Collection<NodePackagesVersionsReader> customReaders) {
    Assert.notNull("customReaders", customReaders);

    return new SeedNodeVersions(
      Stream.concat(customReaders.stream(), Stream.of(new SeedFileSystemNodePackagesVersionReader(filesReader))).toList()
    );
  }
}
