package tech.jhipster.lite.generator.server.sonar.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.sonar.domain.SonarDomainService;
import tech.jhipster.lite.generator.server.sonar.domain.SonarService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SonarBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerService dockerService;

  public SonarBeanConfiguration(ProjectRepository projectRepository, BuildToolService buildToolService, DockerService dockerService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerService = dockerService;
  }

  @Bean
  public SonarService sonarService() {
    return new SonarDomainService(projectRepository, buildToolService, dockerService);
  }
}
