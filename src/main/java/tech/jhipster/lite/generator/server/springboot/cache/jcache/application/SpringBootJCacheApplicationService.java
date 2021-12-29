package tech.jhipster.lite.generator.server.springboot.cache.jcache.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.jcache.domain.SpringBootJCacheService;

@Service
public class SpringBootJCacheApplicationService {

  private final SpringBootJCacheService springBootJCacheService;

  public SpringBootJCacheApplicationService(SpringBootJCacheService springBootJCacheService) {
    this.springBootJCacheService = springBootJCacheService;
  }

  public void addDependencies(Project project) {
    springBootJCacheService.addDependencies(project);
  }

  public void addEnableCaching(Project project) {
    springBootJCacheService.addEnableCaching(project);
  }

  public void addJavaConfig(Project project) {
    springBootJCacheService.addJavaConfig(project);
  }
}
