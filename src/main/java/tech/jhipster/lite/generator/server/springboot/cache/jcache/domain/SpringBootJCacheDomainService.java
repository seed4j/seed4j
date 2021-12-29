package tech.jhipster.lite.generator.server.springboot.cache.jcache.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;

public class SpringBootJCacheDomainService implements SpringBootJCacheService {

  public static final String SOURCE = "server/springboot/cache/jcache";

  private final BuildToolService buildToolService;
  private final SpringBootCacheService springBootCacheService;

  public SpringBootJCacheDomainService(BuildToolService buildToolService, SpringBootCacheService springBootCacheService) {
    this.buildToolService = buildToolService;
    this.springBootCacheService = springBootCacheService;
  }

  @Override
  public void addDependencies(Project project) {
    springBootCacheService.addDependencies(project);
    buildToolService.addDependency(project, SpringBootJCache.cacheApiDependency());
  }

  @Override
  public void addEnableCaching(Project project) {
    springBootCacheService.addEnableCaching(project);
  }
}
