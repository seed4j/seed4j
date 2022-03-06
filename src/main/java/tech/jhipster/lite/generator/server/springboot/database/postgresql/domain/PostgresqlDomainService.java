package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.Postgresql.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class PostgresqlDomainService implements PostgresqlService {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;

  public PostgresqlDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
  }

  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addSpringDataJpa(project);
    addPostgreSQLDriver(project);
    addHikari(project);
    addHibernateCore(project);
    addDockerCompose(project);
    addJavaFiles(project);
    addProperties(project);
    addTestcontainers(project);
    addLoggerInConfiguration(project);
  }

  @Override
  public void addSpringDataJpa(Project project) {
    sqlCommonService.addSpringDataJpa(project);
  }

  @Override
  public void addPostgreSQLDriver(Project project) {
    buildToolService.addDependency(project, psqlDriver());
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
    project.addConfig("postgresqlDockerImage", Postgresql.getPostgresqlDockerImage());
    sqlCommonService.addDockerComposeTemplate(project, DatabaseType.POSTGRESQL.id());
  }

  @Override
  public void addJavaFiles(Project project) {
    sqlCommonService.addJavaFiles(project, DatabaseType.POSTGRESQL.id());
    String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String databasePath = "technical/infrastructure/secondary/postgresql";

    projectRepository.template(project, SOURCE, "FixedPostgreSQL10Dialect.java", getPath(MAIN_JAVA, packageNamePath, databasePath));
    projectRepository.template(project, SOURCE, "FixedPostgreSQL10DialectTest.java", getPath(TEST_JAVA, packageNamePath, databasePath));
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String packageName = project.getPackageName().orElse("com.mycompany.myapp");

    sqlCommonService.addProperties(project, springProperties(baseName, packageName));
  }

  private void addTestcontainers(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    this.sqlCommonService.addTestcontainers(project, DatabaseType.POSTGRESQL.id(), springPropertiesForTest(baseName));
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    sqlCommonService.addLoggers(project);
    addLogger(project, "org.postgresql", Level.WARN);

    addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  public void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    addLoggerTest(project, packageName, level);
  }

  private void addLoggerTest(Project project, String packageName, Level level) {
    springBootCommonService.addLoggerTest(project, packageName, level);
    springBootCommonService.addPropertiesTestLogging(project, packageName, level);
  }
}
