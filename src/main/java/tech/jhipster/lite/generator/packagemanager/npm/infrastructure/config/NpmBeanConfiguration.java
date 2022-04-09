package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmDomainService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmRepository;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class NpmBeanConfiguration {

  private final NpmRepository npmRepository;
  private final ProjectRepository projectRepository;

  public NpmBeanConfiguration(NpmRepository npmRepository, ProjectRepository projectRepository) {
    this.npmRepository = npmRepository;
    this.projectRepository = projectRepository;
  }

  @Bean
  public NpmService npmService() {
    return new NpmDomainService(npmRepository, projectRepository);
  }
}
