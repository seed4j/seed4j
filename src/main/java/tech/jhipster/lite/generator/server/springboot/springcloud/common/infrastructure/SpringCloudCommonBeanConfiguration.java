package tech.jhipster.lite.generator.server.springboot.springcloud.common.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringCloudCommonBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerService dockerService;

  public SpringCloudCommonBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    DockerService dockerService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerService = dockerService;
  }

  @Bean
  public SpringCloudCommonService springCloudCommonService() {
    return new SpringCloudCommonDomainService(projectRepository, buildToolService, dockerService);
  }
}
