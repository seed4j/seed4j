package tech.jhipster.lite.generator.server.springboot.database.mysql.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQL.mysqlConnectorJava;
import static tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQL.springProperties;
import static tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQL.springPropertiesForTest;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public record MySQLDomainService(
  BuildToolService buildToolService,
  SpringBootCommonService springBootCommonService,
  SQLCommonService sqlCommonService,
  DockerService dockerService
)
  implements MySQLService {
  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addSpringDataJpa(project);
    addMySQLDriver(project);
    addHikari(project);
    addHibernateCore(project);
    addDockerCompose(project);
    addJavaFiles(project);
    addProperties(project);
    addLoggerInConfiguration(project);
    addTestcontainers(project);
  }

  @Override
  public void addSpringDataJpa(Project project) {
    sqlCommonService.addSpringDataJpa(project);
  }

  @Override
  public void addMySQLDriver(Project project) {
    buildToolService.addDependency(project, mysqlConnectorJava());
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

    dockerService
      .getImageNameWithVersion(MySQL.getDockerImageName())
      .ifPresentOrElse(
        imageName -> project.addConfig("dockerImageName", imageName),
        () -> {
          throw new GeneratorException("Version not found for docker image: " + MySQL.getDockerImageName());
        }
      );

    sqlCommonService.addDockerComposeTemplate(project, DatabaseType.MYSQL.id());
  }

  @Override
  public void addJavaFiles(Project project) {
    sqlCommonService.addJavaFiles(project, DatabaseType.MYSQL.id());
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    sqlCommonService.addProperties(project, springProperties(baseName));
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    sqlCommonService.addLoggers(project);

    springBootCommonService.addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    springBootCommonService.addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  private void addTestcontainers(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String mysqlVersion = dockerService.getImageVersion(MySQL.getDockerImageName()).orElseThrow();
    this.sqlCommonService.addTestcontainers(project, DatabaseType.MYSQL.id(), springPropertiesForTest(baseName, mysqlVersion));
  }
}
