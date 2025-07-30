package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.infrastructure.secondary.javadependency.maven.MavenProjectJavaDependenciesRepository;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class JavaDependenciesFixture {

  private JavaDependenciesFixture() {}

  public static JavaDependenciesVersionsRepository javaVersionsRepository(
    ProjectFiles filesReader,
    Collection<JavaDependenciesReader> customReaders
  ) {
    Assert.notNull("customReaders", customReaders);

    return new JHipsterJavaDependenciesVersionsRepository(
      Stream.concat(
        customReaders.stream(),
        Stream.of(new JHLiteMavenDependenciesReader(filesReader), new GradleVersionCatalogDependenciesReader(filesReader))
      ).toList()
    );
  }

  public static ProjectJavaDependenciesRepository projectVersionsRepository() {
    return new JHipsterJavaDependenciesRepository(List.of(new MavenProjectJavaDependenciesRepository()));
  }
}
