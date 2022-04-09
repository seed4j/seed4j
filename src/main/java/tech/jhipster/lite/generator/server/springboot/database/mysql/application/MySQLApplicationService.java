package tech.jhipster.lite.generator.server.springboot.database.mysql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQLService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class MySQLApplicationService {

  private final MySQLService mysqlService;

  public MySQLApplicationService(MySQLService mysqlService) {
    this.mysqlService = mysqlService;
  }

  public void init(Project project) {
    mysqlService.init(project);
  }

  public void addSpringDataJpa(Project project) {
    mysqlService.addSpringDataJpa(project);
  }

  public void addMySQLDriver(Project project) {
    mysqlService.addMySQLDriver(project);
  }

  public void addHikari(Project project) {
    mysqlService.addHikari(project);
  }

  public void addHibernateCore(Project project) {
    mysqlService.addHibernateCore(project);
  }

  public void addDockerCompose(Project project) {
    mysqlService.addDockerCompose(project);
  }

  public void addJavaFiles(Project project) {
    mysqlService.addJavaFiles(project);
  }

  public void addProperties(Project project) {
    mysqlService.addProperties(project);
  }

  public void addLogger(Project project) {
    mysqlService.addLoggerInConfiguration(project);
  }
}
