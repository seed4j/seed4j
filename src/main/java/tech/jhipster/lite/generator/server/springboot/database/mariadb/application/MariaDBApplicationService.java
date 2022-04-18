package tech.jhipster.lite.generator.server.springboot.database.mariadb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.domain.MariaDBService;

@Service
public record MariaDBApplicationService(MariaDBService mariaDBService) {
  public void init(Project project) {
    mariaDBService.init(project);
  }

  public void addSpringDataJpa(Project project) {
    mariaDBService.addSpringDataJpa(project);
  }

  public void addMariaDBDriver(Project project) {
    mariaDBService.addMariaDBDriver(project);
  }

  public void addHikari(Project project) {
    mariaDBService.addHikari(project);
  }

  public void addHibernateCore(Project project) {
    mariaDBService.addHibernateCore(project);
  }

  public void addDockerCompose(Project project) {
    mariaDBService.addDockerCompose(project);
  }

  public void addJavaFiles(Project project) {
    mariaDBService.addJavaFiles(project);
  }

  public void addProperties(Project project) {
    mariaDBService.addProperties(project);
  }

  public void addLogger(Project project) {
    mariaDBService.addLoggerInConfiguration(project);
  }
}
