package tech.jhipster.lite.generator.client.react.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.react.core.domain.ReactDomainService;
import tech.jhipster.lite.generator.client.react.core.domain.ReactService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class ReactBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public ReactBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public ReactService reactService() {
    return new ReactDomainService(projectRepository, npmService);
  }
}
