package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.util.Collection;
import java.util.function.Function;
import org.springframework.stereotype.Repository;

@Repository
class Seed4JJavaDependenciesRepository implements ProjectJavaDependenciesRepository {

  private final Function<Seed4JProjectFolder, ProjectJavaDependencies> javaDependencies;

  public Seed4JJavaDependenciesRepository(Collection<Seed4JProjectFolderJavaDependenciesReader> readers) {
    javaDependencies = readJavaDependencies(readers);
  }

  private Function<Seed4JProjectFolder, ProjectJavaDependencies> readJavaDependencies(
    Collection<Seed4JProjectFolderJavaDependenciesReader> readers
  ) {
    return folder ->
      readers
        .stream()
        .map(reader -> reader.get(folder))
        .reduce(ProjectJavaDependencies.EMPTY, ProjectJavaDependencies::merge);
  }

  @Override
  public ProjectJavaDependencies get(Seed4JProjectFolder folder) {
    return javaDependencies.apply(folder);
  }
}
