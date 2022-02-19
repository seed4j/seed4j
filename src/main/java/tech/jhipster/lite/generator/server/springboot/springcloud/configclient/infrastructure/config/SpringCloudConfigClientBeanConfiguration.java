package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigClientDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigClientService;

@Configuration
public class SpringCloudConfigClientBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringCloudCommonService springCloudCommonService;

  public SpringCloudConfigClientBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringCloudCommonService springCloudCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springCloudCommonService = springCloudCommonService;
  }

  @Bean
  public SpringCloudConfigClientService springCloudConfigClientService() {
    return new SpringCloudConfigClientDomainService(projectRepository, buildToolService, springCloudCommonService);
  }
}
