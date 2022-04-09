package tech.jhipster.lite.generator.server.springboot.logging.logstash.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface LogstashService {
  void init(Project project);

  void addDependencies(Project project);
  void addJavaFiles(Project project);
  void addProperties(Project project);
  void addLoggerInConfiguration(Project project);
}
