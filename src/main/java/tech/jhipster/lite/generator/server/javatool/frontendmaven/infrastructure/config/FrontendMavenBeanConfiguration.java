package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.domain.FrontendMavenDomainService;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.domain.FrontendMavenService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class FrontendMavenBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;

  public FrontendMavenBeanConfiguration(BuildToolService buildToolService, ProjectRepository projectRepository) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
  }

  @Bean
  public FrontendMavenService frontendMavenService() {
    return new FrontendMavenDomainService(buildToolService, projectRepository);
  }
}
