package tech.jhipster.lite.generator.server.springboot.cache.jcache.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringBootJCacheService {
  void addDependencies(Project project);
  void addEnableCaching(Project project);
}
