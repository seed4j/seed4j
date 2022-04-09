package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface FlywayService {
  void init(Project project);

  void addFlywayDependency(Project project);

  void addChangelogSql(Project project);

  void addProperties(Project project);

  void addUserAuthorityChangelog(Project project);
}
