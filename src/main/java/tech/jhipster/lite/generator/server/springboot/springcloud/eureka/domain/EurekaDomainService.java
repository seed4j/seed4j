package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaClient.*;

import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public class EurekaDomainService implements EurekaService {

  public static final String SOURCE = "server/springboot/springcloud/eureka";
  public static final String SPRING_CLOUD_SOURCE = "server/springboot/springcloud/configclient";

  public static final String BOOTSTRAP_PROPERTIES_FILE_NAME = "bootstrap.properties";
  public static final String JHIPSTER_REGISTRY_YML_FILE_NAME = "jhipster-registry.yml";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;

  public EurekaDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringCloudCommonService springCloudCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springCloudCommonService = springCloudCommonService;
  }

  @Override
  public void init(Project project) {
    addDependencies(project);
    addProperties(project);
    addDockerCompose(project);
  }

  @Override
  public void addDependencies(Project project) {
    String springCloudVersion =
      this.buildToolService.getVersion(project, "spring-cloud").orElseThrow(() -> new GeneratorException("Spring Cloud version not found"));
    String eurekaClientVersion =
      this.buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")
        .orElseThrow(() -> new GeneratorException("Spring Cloud Netflix Eureka Client version not found"));

    buildToolService.addProperty(project, "spring-cloud.version", springCloudVersion);
    buildToolService.addProperty(project, "spring-cloud-netflix-eureka-client.version", eurekaClientVersion);
    buildToolService.addDependencyManagement(project, springCloudDependencyManagement());
    buildToolService.addDependency(project, springCloudStarterBootstrap());
    buildToolService.addDependency(project, springCloudNetflixEurekaClient());
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      BOOTSTRAP_PROPERTIES_FILE_NAME,
      getPath(MAIN_RESOURCES, CONFIG_FOLDER)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src/test"),
      BOOTSTRAP_PROPERTIES_FILE_NAME,
      getPath(TEST_RESOURCES, CONFIG_FOLDER)
    );
  }

  @Override
  public void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("jhipsterRegistryDockerImage", JHIPSTER_REGISTRY_DOCKER_IMAGE);
    project.addConfig("base64JwtSecret", Base64Utils.getBase64Secret());

    projectRepository.template(
      project,
      SPRING_CLOUD_SOURCE,
      JHIPSTER_REGISTRY_YML_FILE_NAME,
      "src/main/docker",
      JHIPSTER_REGISTRY_YML_FILE_NAME
    );
    projectRepository.template(
      project,
      SPRING_CLOUD_SOURCE,
      "application.config.properties",
      "src/main/docker/central-server-config/localhost-config",
      "application.properties"
    );
  }
}
