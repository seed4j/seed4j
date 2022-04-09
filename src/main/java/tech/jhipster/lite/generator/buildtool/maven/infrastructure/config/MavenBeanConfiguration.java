package tech.jhipster.lite.generator.buildtool.maven.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenDomainService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class MavenBeanConfiguration {

  private final ProjectRepository projectRepository;

  public MavenBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public MavenService mavenService() {
    return new MavenDomainService(projectRepository);
  }
}
