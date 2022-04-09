package tech.jhipster.lite.generator.typescript.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;
import tech.jhipster.lite.generator.typescript.domain.TypescriptDomainService;
import tech.jhipster.lite.generator.typescript.domain.TypescriptService;

@Configuration
public class TypescriptBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public TypescriptBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public TypescriptService typescriptService() {
    return new TypescriptDomainService(projectRepository, npmService);
  }
}
