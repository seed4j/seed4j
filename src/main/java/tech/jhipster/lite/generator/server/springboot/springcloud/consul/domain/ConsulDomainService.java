package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.Consul.*;

import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public class ConsulDomainService implements ConsulService {

  public static final String SOURCE = "server/springboot/springcloud/consul";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;

  public ConsulDomainService(
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
    addDockerConsul(project);
  }

  @Override
  public void addDependencies(Project project) {
    this.buildToolService.getVersion(project, "spring-cloud")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, "spring-cloud.version", version);
          buildToolService.addDependencyManagement(project, springCloudDependencyManagement());
          buildToolService.addDependency(project, springCloudBootstrapDependency());
          buildToolService.addDependency(project, springCloudConsulConfigDependency());
          buildToolService.addDependency(project, springCloudConsulDiscoveryDependency());
        },
        () -> {
          throw new GeneratorException("Spring Cloud version not found");
        }
      );
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      BOOTSTRAP_PROPERTIES,
      getPath(MAIN_RESOURCES, CONFIG_FOLDER)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      BOOTSTRAP_LOCAL_PROPERTIES,
      getPath(MAIN_RESOURCES, CONFIG_FOLDER)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "test"),
      BOOTSTRAP_PROPERTIES,
      getPath(TEST_RESOURCES, CONFIG_FOLDER)
    );
  }

  @Override
  public void addDockerConsul(Project project) {
    project.addConfig("dockerConsulImage", getDockerConsulImage());
    project.addConfig("dockerConsulConfigLoaderImage", getDockerConsulConfigLoaderImage());

    projectRepository.template(project, getPath(SOURCE, "src"), "consul.yml", "src/main/docker", "consul.yml");

    project.addConfig("base64JwtSecret", Base64Utils.getBase64Secret());
    projectRepository.template(
      project,
      getPath(SOURCE, "docker"),
      "application.config.yml",
      "src/main/docker/central-server-config/",
      "application.yml"
    );
  }
}
