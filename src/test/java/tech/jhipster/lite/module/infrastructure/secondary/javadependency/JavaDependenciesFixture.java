package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.FileSystemProjectJavaDependenciesRepository;
import tech.jhipster.lite.shared.error.domain.Assert;

public final class JavaDependenciesFixture {

  private JavaDependenciesFixture() {}

  public static JavaDependenciesVersionsRepository javaVersionsRepository(ProjectFiles filesReader) {
    return javaVersionsRepository(filesReader, List.of());
  }

  public static JavaDependenciesVersionsRepository javaVersionsRepository(
    ProjectFiles filesReader,
    Collection<JavaDependenciesReader> customReaders
  ) {
    Assert.notNull("customReaders", customReaders);

    return new JHipsterJavaDependenciesVersionsRepository(
      Stream.concat(Stream.of(new FileSystemJavaDependenciesReader(filesReader)), customReaders.stream()).toList()
    );
  }

  public static ProjectJavaDependenciesRepository projectVersionsRepository() {
    return new FileSystemProjectJavaDependenciesRepository();
  }
}
