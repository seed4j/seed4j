package tech.jhipster.lite.generator.server.springboot.database.mariadb.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface MariaDBService {
  void init(Project project);

  void addSpringDataJpa(Project project);
  void addMariaDBDriver(Project project);
  void addHikari(Project project);
  void addHibernateCore(Project project);
  void addDockerCompose(Project project);
  void addJavaFiles(Project project);
  void addProperties(Project project);
  void addLoggerInConfiguration(Project project);
}
