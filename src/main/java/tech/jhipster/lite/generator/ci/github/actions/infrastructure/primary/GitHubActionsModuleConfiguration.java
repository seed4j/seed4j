package tech.jhipster.lite.generator.ci.github.actions.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsApplicationService;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class GitHubActionsModuleConfiguration {

  @Bean
  JHipsterModuleResource gutHubActionsModule(GitHubActionsApplicationService gitHubActions) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/developer-tools/github-actions")
      .slug("github-actions")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Continuous Integration", "Add GitHub Actions for Maven Build"))
      .tags("ci", "github")
      .factory(gitHubActions::buildModule);
  }
}
