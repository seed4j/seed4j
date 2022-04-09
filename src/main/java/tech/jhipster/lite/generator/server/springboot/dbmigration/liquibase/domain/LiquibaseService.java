package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface LiquibaseService {
  void init(Project project);

  void addLiquibase(Project project);
  void addChangelogMasterXml(Project project);
  void addChangelogXml(Project project, String path, String fileName);
  void addConfigurationJava(Project project);
  void addLoggerInConfiguration(Project project);

  void addUserAuthorityChangelog(Project project);
}
