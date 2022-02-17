package tech.jhipster.lite.generator.client.angular.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.core.domain.AngularDomainService;
import tech.jhipster.lite.generator.client.angular.core.domain.AngularService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class AngularBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public AngularBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public AngularService angularService() {
    return new AngularDomainService(projectRepository, npmService);
  }
}
