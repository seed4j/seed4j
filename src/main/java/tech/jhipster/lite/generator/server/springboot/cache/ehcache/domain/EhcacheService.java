package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface EhcacheService {
  void init(Project project);

  void addDependencies(Project project);

  void addJavaFiles(Project project);

  void addProperties(Project project);
}
