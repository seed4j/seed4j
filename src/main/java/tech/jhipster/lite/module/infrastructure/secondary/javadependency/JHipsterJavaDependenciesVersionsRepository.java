package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.shared.memoizer.domain.Memoizers;

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
