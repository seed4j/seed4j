package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface EhcacheService {
  void initJavaConfiguration(Project project);
  void initXmlConfiguration(Project project);
  void addDependencies(Project project);
  void addXmlDependencies(Project project);
  void addEnableCaching(Project project);
  void addJavaConfig(Project project);
  void addJavaProperties(Project project);
  void addEhcacheXml(Project project);
  void addXmlProperty(Project project);
}
