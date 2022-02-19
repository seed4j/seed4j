package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfig.*;

import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public class SpringCloudConfigClientDomainService implements SpringCloudConfigClientService {

  private static final String SOURCE = "server/springboot/springcloud/configclient";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringCloudCommonService springCloudCommonService;

  public SpringCloudConfigClientDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringCloudCommonService springCloudCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springCloudCommonService = springCloudCommonService;
  }

  @Override
  public void init(Project project) {
    addCloudConfigDependencies(project);
    addProperties(project);
    addDockerCompose(project);
  }

  @Override
  public void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("jhipsterRegistryDockerImage", SpringCloudConfig.getJhipsterRegistryDockerImage());
    projectRepository.template(project, SOURCE, "jhipster-registry.yml", "src/main/docker", "jhipster-registry.yml");

    project.addConfig("base64JwtSecret", Base64Utils.getBase64Secret());
    projectRepository.template(
      project,
      SOURCE,
      "application.config.properties",
      "src/main/docker/central-server-config/localhost-config",
      "application.properties"
    );
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      "bootstrap.properties",
      getPath(MAIN_RESOURCES, CONFIG_FOLDER)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      "bootstrap-local.properties",
      getPath(MAIN_RESOURCES, CONFIG_FOLDER)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src", "test"),
      "bootstrap.properties",
      getPath(TEST_RESOURCES, CONFIG_FOLDER)
    );
  }

  @Override
  public void addCloudConfigDependencies(Project project) {
    this.buildToolService.getVersion(project, "spring-cloud")
      .ifPresentOrElse(
        version -> {
          this.buildToolService.addProperty(project, "spring-cloud.version", version);
          this.buildToolService.addDependencyManagement(project, springCloudDependencies());
          this.buildToolService.addDependency(project, springCloudBootstrap());
          this.buildToolService.addDependency(project, springCloudConfigClient());
        },
        () -> {
          throw new GeneratorException("Spring Cloud version not found");
        }
      );
  }
}
