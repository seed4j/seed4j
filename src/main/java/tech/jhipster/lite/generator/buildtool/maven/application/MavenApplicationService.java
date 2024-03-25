package tech.jhipster.lite.generator.buildtool.maven.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.Version;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MavenApplicationService {

  private final MavenModuleFactory factory;
  private final JavaDependenciesVersionsRepository javaDependenciesVersionsRepository;

  public MavenApplicationService(JavaDependenciesVersionsRepository javaDependenciesVersionsRepository) {
    this.javaDependenciesVersionsRepository = javaDependenciesVersionsRepository;
    factory = new MavenModuleFactory();
  }

  public JHipsterModule buildMavenModule(JHipsterModuleProperties properties) {
    return factory.buildMavenModule(properties, getMavenVersion());
  }

  public JHipsterModule buildMavenWrapperModule(JHipsterModuleProperties properties) {
    return factory.buildMavenWrapperModule(properties, getMavenVersion());
  }

  private Version getMavenVersion() {
    return javaDependenciesVersionsRepository.get().get(new VersionSlug("maven.version")).version();
  }
}
