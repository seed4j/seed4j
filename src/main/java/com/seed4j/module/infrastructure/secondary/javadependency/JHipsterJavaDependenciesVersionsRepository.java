package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.shared.memoizer.domain.Memoizers;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;

@Repository
class JHipsterJavaDependenciesVersionsRepository implements JavaDependenciesVersionsRepository {

  private final Supplier<JavaDependenciesVersions> versions;

  public JHipsterJavaDependenciesVersionsRepository(Collection<JavaDependenciesReader> readers) {
    versions = Memoizers.of(readVersions(readers));
  }

  private Supplier<JavaDependenciesVersions> readVersions(Collection<JavaDependenciesReader> readers) {
    return () -> readers.stream().map(JavaDependenciesReader::get).reduce(JavaDependenciesVersions.EMPTY, JavaDependenciesVersions::merge);
  }

  @Override
  public JavaDependenciesVersions get() {
    return versions.get();
  }
}
