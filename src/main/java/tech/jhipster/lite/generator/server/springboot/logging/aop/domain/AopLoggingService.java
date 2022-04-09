package tech.jhipster.lite.generator.server.springboot.logging.aop.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface AopLoggingService {
  void init(Project project);

  void addMavenDependencies(Project project);
  void addJavaFiles(Project project);
  void addProperties(Project project);
}
