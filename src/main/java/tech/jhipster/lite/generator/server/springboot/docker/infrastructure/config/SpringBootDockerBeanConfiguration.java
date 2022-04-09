package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.docker.domain.SpringBootDockerDomainService;
import tech.jhipster.lite.generator.server.springboot.docker.domain.SpringBootDockerService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootDockerBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;

  public SpringBootDockerBeanConfiguration(ProjectRepository projectRepository, BuildToolService buildToolService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
  }

  @Bean
  public SpringBootDockerService springBootDockerService() {
    return new SpringBootDockerDomainService(projectRepository, buildToolService);
  }
}
