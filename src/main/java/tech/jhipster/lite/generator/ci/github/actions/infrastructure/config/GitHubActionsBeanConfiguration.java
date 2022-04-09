package tech.jhipster.lite.generator.ci.github.actions.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.github.actions.domain.GitHubActionsDomainService;
import tech.jhipster.lite.generator.ci.github.actions.domain.GitHubActionsService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class GitHubActionsBeanConfiguration {

  private final ProjectRepository projectRepository;

  public GitHubActionsBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public GitHubActionsService gitHubActionsService() {
    return new GitHubActionsDomainService(projectRepository);
  }
}
