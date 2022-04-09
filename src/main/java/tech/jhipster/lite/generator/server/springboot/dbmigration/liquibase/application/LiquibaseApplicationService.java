package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseService liquibaseService;

  public LiquibaseApplicationService(LiquibaseService liquibaseService) {
    this.liquibaseService = liquibaseService;
  }

  public void init(Project project) {
    liquibaseService.init(project);
  }

  public void addLiquibase(Project project) {
    liquibaseService.addLiquibase(project);
  }

  void addChangelogMasterXml(Project project) {
    liquibaseService.addChangelogMasterXml(project);
  }

  void addChangelogXml(Project project, String path, String fileName) {
    liquibaseService.addChangelogXml(project, path, fileName);
  }

  void addConfigurationJava(Project project) {
    liquibaseService.addConfigurationJava(project);
  }

  void addLoggerInConfiguration(Project project) {
    liquibaseService.addLoggerInConfiguration(project);
  }

  public void addUserAuthorityChangelog(Project project) {
    liquibaseService.addUserAuthorityChangelog(project);
  }
}
