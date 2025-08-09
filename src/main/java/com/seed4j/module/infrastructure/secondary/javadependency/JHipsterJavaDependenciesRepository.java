package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.util.Collection;
import java.util.function.Function;
import org.springframework.stereotype.Repository;

@Repository
class JHipsterJavaDependenciesRepository implements ProjectJavaDependenciesRepository {

  private final Function<SeedProjectFolder, ProjectJavaDependencies> javaDependencies;

  public JHipsterJavaDependenciesRepository(Collection<JHipsterProjectFolderJavaDependenciesReader> readers) {
    javaDependencies = readJavaDependencies(readers);
  }

  private Function<SeedProjectFolder, ProjectJavaDependencies> readJavaDependencies(
    Collection<JHipsterProjectFolderJavaDependenciesReader> readers
  ) {
    return folder ->
      readers
        .stream()
        .map(reader -> reader.get(folder))
        .reduce(ProjectJavaDependencies.EMPTY, ProjectJavaDependencies::merge);
  }

  @Override
  public ProjectJavaDependencies get(SeedProjectFolder folder) {
    return javaDependencies.apply(folder);
  }
}
