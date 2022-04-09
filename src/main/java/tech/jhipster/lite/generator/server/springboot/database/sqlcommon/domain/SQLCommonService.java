package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain;

import java.util.Map;
import tech.jhipster.lite.generator.tools.domain.Project;

public interface SQLCommonService {
  void addTestcontainers(Project project, String database, Map<String, Object> properties);
  void addSpringDataJpa(Project project);
  void addHikari(Project project);
  void addHibernateCore(Project project);
  void addDockerComposeTemplate(Project project, String database);
  void addJavaFiles(Project project, String database);
  void addProperties(Project project, Map<String, Object> properties);
  void addLoggers(Project project);
}
