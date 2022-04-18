package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaClient.springCloudNetflixEurekaClient;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public record EurekaDomainService(BuildToolService buildToolService, SpringCloudCommonService springCloudCommonService)
  implements EurekaService {
  public static final String SOURCE = "server/springboot/springcloud/eureka";

  public static final String BOOTSTRAP_PROPERTIES_FILE_NAME = "bootstrap.properties";

  @Override
  public void init(Project project) {
    addDependencies(project);
    addProperties(project);
    addDockerCompose(project);
  }

  @Override
  public void addDependencies(Project project) {
    String eurekaClientVersion =
      this.buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")
        .orElseThrow(() -> new GeneratorException("Spring Cloud Netflix Eureka Client version not found"));

    springCloudCommonService.addSpringCloudCommonDependencies(project);

    buildToolService.addProperty(project, "spring-cloud-netflix-eureka-client.version", eurekaClientVersion);
    buildToolService.addDependency(project, springCloudNetflixEurekaClient());
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);
    String baseName = project.getBaseName().orElse(BASE_NAME);
    project.addConfig("baseNameLowercase", baseName.toLowerCase());

    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      BOOTSTRAP_PROPERTIES_FILE_NAME,
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src/test"),
      BOOTSTRAP_PROPERTIES_FILE_NAME,
      getPath(TEST_RESOURCES, CONFIG)
    );
  }

  @Override
  public void addDockerCompose(Project project) {
    springCloudCommonService.addJhipsterRegistryDockerCompose(project);
  }
}
