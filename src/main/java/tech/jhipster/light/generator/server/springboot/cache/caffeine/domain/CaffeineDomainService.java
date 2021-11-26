package tech.jhipster.light.generator.server.springboot.cache.caffeine.domain;

import tech.jhipster.light.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.light.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.light.generator.project.domain.Project;

public class CaffeineDomainService implements CaffeineService {

  private final BuildToolService buildToolService;

  public CaffeineDomainService(BuildToolService buildToolService) {
    this.buildToolService = buildToolService;
  }

  @Override
  public void initCache(Project project) {
    Dependency cacheDependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-cache").build();
    buildToolService.addDependency(project, cacheDependency);
  }
}
