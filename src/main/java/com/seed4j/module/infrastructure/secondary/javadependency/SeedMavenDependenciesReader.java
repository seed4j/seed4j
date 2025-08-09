package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Order
@Repository
class SeedMavenDependenciesReader implements JavaDependenciesReader {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";

  private final FileSystemMavenDependenciesReader reader;

  public SeedMavenDependenciesReader(ProjectFiles files) {
    this.reader = new FileSystemMavenDependenciesReader(files, CURRENT_VERSIONS_FILE);
  }

  @Override
  public JavaDependenciesVersions get() {
    return reader.get();
  }
}
