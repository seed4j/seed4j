package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.nodejs.NodePackagesVersions;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.shared.memoizer.domain.Memoizers;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;

@Repository
class Seed4JNodeVersions implements NodeVersions {

  private final Supplier<NodePackagesVersions> versions;

  public Seed4JNodeVersions(Collection<NodePackagesVersionsReader> readers) {
    versions = Memoizers.of(versionsReader(readers));
  }

  private Supplier<NodePackagesVersions> versionsReader(Collection<NodePackagesVersionsReader> readers) {
    return () -> readers.stream().map(NodePackagesVersionsReader::get).reduce(NodePackagesVersions.EMPTY, NodePackagesVersions::merge);
  }

  @Override
  public NodePackagesVersions get() {
    return versions.get();
  }
}
