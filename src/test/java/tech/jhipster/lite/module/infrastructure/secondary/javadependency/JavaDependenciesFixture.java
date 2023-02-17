package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.util.List;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.FileSystemProjectJavaDependenciesRepository;

public final class JavaDependenciesFixture {

  private JavaDependenciesFixture() {}

  public static JavaDependenciesVersionsRepository javaVersionsRepository(ProjectFilesReader filesReader) {
    return new JHipsterJavaDependenciesVersionsRepository(List.of(new FileSystemJavaDependenciesReader(filesReader)));
  }

  public static ProjectJavaDependenciesRepository projectVersionsRepository() {
    return new FileSystemProjectJavaDependenciesRepository();
  }
}
