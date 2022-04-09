package tech.jhipster.lite.generator.server.springboot.springcloud.consul.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class ConsulBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;
  private final DockerService dockerService;

  public ConsulBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringCloudCommonService springCloudCommonService,
    DockerService dockerService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springCloudCommonService = springCloudCommonService;
    this.dockerService = dockerService;
  }

  @Bean
  public ConsulService consulService() {
    return new ConsulDomainService(buildToolService, projectRepository, springCloudCommonService, dockerService);
  }
}
