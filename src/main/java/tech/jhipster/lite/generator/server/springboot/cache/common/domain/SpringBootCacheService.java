package tech.jhipster.lite.generator.server.springboot.cache.common.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringBootCacheService {
  void addDependencies(Project project);
  void addEnableCaching(Project project);
}
