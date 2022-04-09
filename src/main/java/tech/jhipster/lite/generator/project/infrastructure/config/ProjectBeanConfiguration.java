package tech.jhipster.lite.generator.project.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectDomainService;
import tech.jhipster.lite.generator.project.domain.ProjectService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class ProjectBeanConfiguration {

  private final NpmService npmService;
  private final ProjectRepository projectRepository;

  public ProjectBeanConfiguration(NpmService npmService, ProjectRepository projectRepository) {
    this.npmService = npmService;
    this.projectRepository = projectRepository;
  }

  @Bean
  public ProjectService initService() {
    return new ProjectDomainService(npmService, projectRepository);
  }
}
