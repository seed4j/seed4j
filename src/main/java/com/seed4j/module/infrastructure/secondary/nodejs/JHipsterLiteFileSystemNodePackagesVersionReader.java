package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.nodejs.NodePackagesVersions;
import com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Order
@Repository
public class JHipsterLiteFileSystemNodePackagesVersionReader implements NodePackagesVersionsReader {

  private final FileSystemNodePackagesVersionReader reader;

  public JHipsterLiteFileSystemNodePackagesVersionReader(ProjectFiles projectFiles) {
    reader = new FileSystemNodePackagesVersionReader(
      projectFiles,
      List.of(SeedNodePackagesVersionSource.values()),
      "/generator/dependencies/"
    );
  }

  @Override
  public NodePackagesVersions get() {
    return reader.get();
  }
}
