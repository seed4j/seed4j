package com.seed4j.generator.ci.gitlab.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.GITLAB_CI;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GITLAB_CI_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GITLAB_CI_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.ci.gitlab.application.GitLabCiApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitLabCiModuleConfiguration {

  private static final String CI_TAG = "ci";
  private static final String GITLAB_TAG = "gitlab";
  private static final String CONTINUOUS_INTEGRATION_GROUP_DOC = "Continuous Integration";

  @Bean
  SeedModuleResource gitLabCiMavenModule(GitLabCiApplicationService gitLabCi) {
    return SeedModuleResource.builder()
      .slug(GITLAB_CI_MAVEN)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Maven Build")
      .organization(SeedModuleOrganization.builder().feature(GITLAB_CI).addDependency(MAVEN_JAVA).build())
      .tags(CI_TAG, GITLAB_TAG)
      .factory(gitLabCi::buildGitLabCiMavenModule);
  }

  @Bean
  SeedModuleResource gitLabCiGradleModule(GitLabCiApplicationService gitLabCi) {
    return SeedModuleResource.builder()
      .slug(GITLAB_CI_GRADLE)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Gradle Build")
      .organization(SeedModuleOrganization.builder().feature(GITLAB_CI).addDependency(GRADLE_JAVA).build())
      .tags(CI_TAG, GITLAB_TAG)
      .factory(gitLabCi::buildGitLabCiGradleModule);
  }
}
