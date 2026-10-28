package com.seed4j.generator.ci.gitlab;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.GITLAB_CI;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITLAB_CI_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITLAB_CI_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class GitLabCiModuleResources {

  private static final String CI_TAG = "ci";
  private static final String GITLAB_TAG = "gitlab";
  private static final String CONTINUOUS_INTEGRATION_GROUP_DOC = "Continuous Integration";

  private GitLabCiModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Maven implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(GITLAB_CI_MAVEN)
        .withoutProperties()
        .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Maven Build")
        .organization(Seed4JModuleOrganization.builder().feature(GITLAB_CI).addDependency(MAVEN_JAVA).build())
        .tags(CI_TAG, GITLAB_TAG)
        .factory(new GitLabCiModuleFactory()::buildGitLabCiMavenModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Gradle implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(GITLAB_CI_GRADLE)
        .withoutProperties()
        .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitLab CI for Gradle Build")
        .organization(Seed4JModuleOrganization.builder().feature(GITLAB_CI).addDependency(GRADLE_JAVA).build())
        .tags(CI_TAG, GITLAB_TAG)
        .factory(new GitLabCiModuleFactory()::buildGitLabCiGradleModule);
    }
  }
}
