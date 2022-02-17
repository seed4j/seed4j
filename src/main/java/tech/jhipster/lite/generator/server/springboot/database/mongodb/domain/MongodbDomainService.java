package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.Mongodb.mongodbDriver;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class MongodbDomainService implements MongodbService {

  public static final String SOURCE = "server/springboot/database/mongodb";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public MongodbDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
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
    project.addConfig("mongodbDockerImage", Mongodb.getMongodbDockerImage());
    projectRepository.template(project, SOURCE, "mongodb.yml", "src/main/docker", "mongodb.yml");
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String mongodbPath = "technical/infrastructure/secondary/mongodb";

    projectRepository.template(project, SOURCE, "MongodbDatabaseConfiguration.java", getPath(MAIN_JAVA, packageNamePath, mongodbPath));
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

  @Override
  public void addTestcontainers(Project project) {
    this.buildToolService.getVersion(project, "testcontainers")
      .ifPresentOrElse(
        version -> {
          Dependency dependency = Dependency
            .builder()
            .groupId("org.testcontainers")
            .artifactId("mongodb")
            .version("\\${testcontainers.version}")
            .scope("test")
            .build();
          buildToolService.addProperty(project, "testcontainers.version", version);
          buildToolService.addDependency(project, dependency);
        },
        () -> {
          throw new GeneratorException("Testcontainers version not found");
        }
      );
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
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String integrationTestPath = getPath(TEST_JAVA, packageNamePath);

    String oldImport = "import org.springframework.boot.test.context.SpringBootTest;";
    String newImport =
      """
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.springframework.boot.test.context.SpringBootTest;""";
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldImport, newImport);

    String oldAnnotation = "public @interface";
    String newAnnotation = """
      @ExtendWith(MongodbTestContainerExtension.class)
      public @interface""";
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldAnnotation, newAnnotation);
  }
}
