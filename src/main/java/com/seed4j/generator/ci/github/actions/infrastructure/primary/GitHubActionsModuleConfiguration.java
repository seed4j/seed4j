package com.seed4j.generator.ci.github.actions.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.GITHUB_ACTIONS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_ACTIONS_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_ACTIONS_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.ci.github.actions.application.GitHubActionsApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitHubActionsModuleConfiguration {

  private static final String CI_TAG = "ci";
  private static final String GITHUB_TAG = "github";
  private static final String CONTINUOUS_INTEGRATION_GROUP_DOC = "Continuous Integration";

  @Bean
  Seed4JModuleResource gitHubActionsMavenModule(GitHubActionsApplicationService gitHubActions) {
    return Seed4JModuleResource.builder()
      .slug(GITHUB_ACTIONS_MAVEN)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitHub Actions for Maven Build")
      .organization(Seed4JModuleOrganization.builder().feature(GITHUB_ACTIONS).addDependency(MAVEN_JAVA).build())
      .tags(CI_TAG, GITHUB_TAG)
      .factory(gitHubActions::buildGitHubActionsMavenModule);
  }

  @Bean
  Seed4JModuleResource gitHubActionsGradleModule(GitHubActionsApplicationService gitHubActions) {
    return Seed4JModuleResource.builder()
      .slug(GITHUB_ACTIONS_GRADLE)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitHub Actions for Gradle Build")
      .organization(Seed4JModuleOrganization.builder().feature(GITHUB_ACTIONS).addDependency(GRADLE_WRAPPER).build())
      .tags(CI_TAG, GITHUB_TAG)
      .factory(gitHubActions::buildGitHubActionsGradleModule);
  }
}
