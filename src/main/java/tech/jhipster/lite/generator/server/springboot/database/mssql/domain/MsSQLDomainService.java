package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class MsSQLDomainService implements MsSQLService {

  public static final String SOURCE = "server/springboot/database/mssql";
  public static final String EXTENSION_INTEGRATION_TEST_CLASS = "MsSQLTestContainerExtension";

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;
  private final DockerImages dockerImages;
  private final ProjectRepository projectRepository;

  public MsSQLDomainService(
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService,
    DockerImages dockerImages,
    ProjectRepository projectRepository
  ) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
    this.dockerImages = dockerImages;
    this.projectRepository = projectRepository;
  }

  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addSpringData(project);
    addDriver(project);
    addDockerCompose(project);
    addJavaFiles(project);
    addHikari(project);
    addProperties(project);
    addHibernateCore(project);
    addTestcontainers(project);
    addLoggerInConfiguration(project);
  }

  private void addSpringData(Project project) {
    sqlCommonService.addSpringDataJpa(project);
  }

  private void addDriver(Project project) {
    buildToolService.addDependency(project, MsSQL.driver());
  }

  private void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);

    String dockerImage = dockerImages.get(MsSQL.getDockerImageName()).fullName();
    project.addConfig("dockerImageName", dockerImage);

    sqlCommonService.addDockerComposeTemplate(project, DatabaseType.MSSQL.id());
  }

  private void addHikari(Project project) {
    sqlCommonService.addHikari(project);
  }

  private void addJavaFiles(Project project) {
    sqlCommonService.addJavaFiles(project, DatabaseType.MSSQL.id());
  }

  private void addProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Database Configuration");

    MsSQL
      .springProperties(project.getBaseName().orElse("jhipster"))
      .forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);
  }

  private void addTestcontainers(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String integrationTestPath = getPath(TEST_JAVA, packageNamePath);
    sqlCommonService.addTestcontainers(project, "mssqlserver", MsSQL.springPropertiesForTest(project.getBaseName().orElse("jhipster")));
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, MsSQL.LICENSE_TEST_CONTAINER_FILE).withDestinationFolder(TEST_RESOURCES)
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, MsSQL.MSSQL_TEST_CONTAINER_EXTENSION_FILE)
        .withDestinationFolder(getPath(integrationTestPath))
    );
    springBootCommonService.updateIntegrationTestAnnotation(project, EXTENSION_INTEGRATION_TEST_CLASS);
  }

  private void addHibernateCore(Project project) {
    sqlCommonService.addHibernateCore(project);
  }

  private void addLoggerInConfiguration(Project project) {
    sqlCommonService.addLoggers(project);
    addLogger(project, "org.reflections", Level.WARN);
    addLogger(project, MsSQL.driver().getGroupId(), Level.WARN);

    springBootCommonService.addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    springBootCommonService.addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  private void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    springBootCommonService.addLoggerTest(project, packageName, level);
  }
}
