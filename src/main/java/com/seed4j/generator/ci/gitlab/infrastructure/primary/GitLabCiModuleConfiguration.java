package com.seed4j.generator.ci.gitlab.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.GITLAB_CI;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITLAB_CI_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITLAB_CI_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.ci.gitlab.application.GitLabCiApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitLabCiModuleConfiguration {

  private static final String CI_TAG = "ci";
  private static final String GITLAB_TAG = "gitlab";
  private static final String CONTINUOUS_INTEGRATION_GROUP_DOC = "Continuous Integration";

  @Bean
  Seed4JModuleResource gitLabCiMavenModule(GitLabCiApplicationService gitLabCi) {
    return Seed4JModuleResource.builder()
      .slug(GITLAB_CI_MAVEN)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Maven Build")
      .organization(Seed4JModuleOrganization.builder().feature(GITLAB_CI).addDependency(MAVEN_JAVA).build())
      .tags(CI_TAG, GITLAB_TAG)
      .factory(gitLabCi::buildGitLabCiMavenModule);
  }

  @Bean
  Seed4JModuleResource gitLabCiGradleModule(GitLabCiApplicationService gitLabCi) {
    return Seed4JModuleResource.builder()
      .slug(GITLAB_CI_GRADLE)
      .withoutProperties()
      .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Gradle Build")
      .organization(Seed4JModuleOrganization.builder().feature(GITLAB_CI).addDependency(GRADLE_JAVA).build())
      .tags(CI_TAG, GITLAB_TAG)
      .factory(gitLabCi::buildGitLabCiGradleModule);
  }
}
