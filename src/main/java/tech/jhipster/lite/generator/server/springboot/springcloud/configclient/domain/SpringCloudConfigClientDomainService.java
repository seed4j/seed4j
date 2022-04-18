package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfig.springCloudConfigClient;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public record SpringCloudConfigClientDomainService(BuildToolService buildToolService, SpringCloudCommonService springCloudCommonService)
  implements SpringCloudConfigClientService {
  private static final String SOURCE = "server/springboot/springcloud/configclient";

  @Override
  public void init(Project project) {
    addCloudConfigDependencies(project);
    addProperties(project);
    addDockerCompose(project);
  }

  @Override
  public void addDockerCompose(Project project) {
    springCloudCommonService.addJhipsterRegistryDockerCompose(project);
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      "bootstrap.properties",
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      "bootstrap-local.properties",
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src", "test"),
      "bootstrap.properties",
      getPath(TEST_RESOURCES, CONFIG)
    );
  }

  @Override
  public void addCloudConfigDependencies(Project project) {
    this.buildToolService.getVersion(project, "spring-cloud")
      .ifPresentOrElse(
        version -> {
          this.springCloudCommonService.addSpringCloudCommonDependencies(project);
          this.buildToolService.addDependency(project, springCloudConfigClient());
        },
        () -> {
          throw new GeneratorException("Spring Cloud version not found");
        }
      );
  }
}
