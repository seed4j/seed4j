package tech.jhipster.lite.generator.ci.github.actions.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitHubActionsModuleConfiguration {

  @Bean
  JHipsterModuleResource gitHubActionsModule(GitHubActionsApplicationService gitHubActions) {
    return JHipsterModuleResource
      .builder()
      .slug(GITHUB_ACTIONS)
      .withoutProperties()
      .apiDoc("Continuous Integration", "Add GitHub Actions for Maven Build")
      .organization(JHipsterModuleOrganization.builder().addDependency(MAVEN_JAVA).build())
      .tags("ci", "github")
      .factory(gitHubActions::buildModule);
  }
}
