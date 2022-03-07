package tech.jhipster.lite.generator.server.springboot.database.mariadb.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.database.mariadb.domain.MariaDB.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class MariaDBDomainService implements MariaDBService {

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;

  public MariaDBDomainService(
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService
  ) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
  }

  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addJavaFiles(project);
    addMariaDBDriver(project);
    addHikari(project);
    addHibernateCore(project);
    addDockerCompose(project);
    addProperties(project);
    addTestcontainers(project);
    addLoggerInConfiguration(project);
    addSpringDataJpa(project);
  }

  @Override
  public void addMariaDBDriver(Project project) {
    buildToolService.addDependency(project, mariadbConnectorJava());
  }

  @Override
  public void addSpringDataJpa(Project project) {
    sqlCommonService.addSpringDataJpa(project);
  }

  @Override
  public void addHikari(Project project) {
    sqlCommonService.addHikari(project);
  }

  @Override
  public void addHibernateCore(Project project) {
    sqlCommonService.addHibernateCore(project);
  }

  @Override
  public void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("dockerImageName", MariaDB.getDockerImageName());
    sqlCommonService.addDockerComposeTemplate(project, DatabaseType.MARIADB.id());
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    sqlCommonService.addProperties(project, springProperties(baseName));
  }

  @Override
  public void addJavaFiles(Project project) {
    sqlCommonService.addJavaFiles(project, DatabaseType.MARIADB.id());
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    sqlCommonService.addLoggers(project);

    addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  private void addTestcontainers(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    this.sqlCommonService.addTestcontainers(project, DatabaseType.MARIADB.id(), springPropertiesForTest(baseName));
  }

  private void addLoggerTest(Project project, String packageName, Level level) {
    springBootCommonService.addLoggerTest(project, packageName, level);
    springBootCommonService.addPropertiesTestLogging(project, packageName, level);
  }
}
