package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface EhcacheService {
  void initJavaConfiguration(Project project);

  void addDependencies(Project project);

  void addEnableCaching(Project project);

  void addJavaConfig(Project project);

  void addJavaProperties(Project project);
}
