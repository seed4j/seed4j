package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.Mongodb.mongodbDriver;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class MongodbDomainService implements MongodbService {

  public static final String SOURCE = "server/springboot/database/mongodb";
  public static final String SECURITY_SOURCE = "server/springboot/mvc/security/";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;
  private final DockerService dockerService;

  public MongodbDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService,
    DockerService dockerService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
    this.dockerService = dockerService;
  }

  @Override
  public void init(Project project) {
    addSpringDataMongodb(project);
    addMongodbDriver(project);
    addDockerCompose(project);
    addJavaFiles(project);
    addConfigurationFiles(project);
    addProperties(project);
    addTestcontainers(project);
    addLoggerInConfiguration(project);

    updateIntegrationTestAnnotation(project);
  }

  @Override
  public void addSpringDataMongodb(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-mongodb").build();

    buildToolService.addDependency(project, dependency);
  }

  @Override
  public void addMongodbDriver(Project project) {
    buildToolService.addDependency(project, mongodbDriver());
  }

  @Override
  public void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);

    dockerService
      .getImageNameWithVersion(Mongodb.getMongodbDockerImageName())
      .ifPresentOrElse(
        imageName -> project.addConfig("mongodbDockerImage", imageName),
        () -> {
          throw new GeneratorException("Version not found for docker image: " + Mongodb.getMongodbDockerImageName());
        }
      );
    sqlCommonService.addDockerComposeTemplate(project, "mongodb");
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String mongodbPath = "technical/infrastructure/secondary/mongodb";

    if (isSpringSecurityDependencyAdded(project)) {
      projectRepository.template(
        project,
        SOURCE,
        "MongodbDatabaseConfiguration_with_mongo_auditing.java",
        getPath(MAIN_JAVA, packageNamePath, mongodbPath),
        "MongodbDatabaseConfiguration.java"
      );
      projectRepository.template(
        project,
        SECURITY_SOURCE,
        "SpringSecurityAuditorAware.java",
        getPath(MAIN_JAVA, packageNamePath, "security")
      );
    } else {
      projectRepository.template(project, SOURCE, "MongodbDatabaseConfiguration.java", getPath(MAIN_JAVA, packageNamePath, mongodbPath));
    }

    projectRepository.template(project, SOURCE, "JSR310DateConverters.java", getPath(MAIN_JAVA, packageNamePath, mongodbPath));

    projectRepository.template(project, SOURCE, "JSR310DateConvertersTest.java", getPath(TEST_JAVA, packageNamePath, mongodbPath));
    projectRepository.template(project, SOURCE, "MongodbTestContainerExtension.java", getPath(TEST_JAVA, packageNamePath));
    projectRepository.template(project, SOURCE, "TestContainersSpringContextCustomizerFactory.java", getPath(TEST_JAVA, packageNamePath));
  }

  @Override
  public void addConfigurationFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE, "spring.factories", getPath(TEST_RESOURCES, "META-INF"));
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    springBootCommonService.addPropertiesComment(project, "Database Configuration");

    springPropertiesMongodb(baseName).forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);
  }

  private Map<String, Object> springPropertiesMongodb(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("spring.data.mongodb.database", baseName);
    result.put("spring.data.mongodb.uri", "mongodb://localhost:27017");

    return result;
  }

  private void addTestcontainers(Project project) {
    this.sqlCommonService.addTestcontainers(project, DatabaseType.MONGODB.id(), Map.of());
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    addLogger(project, "org.reflections", Level.WARN);
    addLogger(project, "org.mongodb.driver", Level.WARN);

    springBootCommonService.addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    springBootCommonService.addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  public void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    springBootCommonService.addLoggerTest(project, packageName, level);
  }

  private void updateIntegrationTestAnnotation(Project project) {
    springBootCommonService.updateIntegrationTestAnnotation(project, "MongodbTestContainerExtension");
  }

  private boolean isSpringSecurityDependencyAdded(Project project) {
    Dependency springSecurityTestDependency = Dependency
      .builder()
      .groupId("org.springframework.security")
      .artifactId("spring-security-test")
      .scope("test")
      .build();
    return buildToolService.isDependencyExist(project, springSecurityTestDependency);
  }
}
