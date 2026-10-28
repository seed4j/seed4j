package com.seed4j.generator.ci.github.actions;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.GITHUB_ACTIONS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_ACTIONS_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_ACTIONS_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class GitHubActionsModuleResources {

  private static final String CI_TAG = "ci";
  private static final String GITHUB_TAG = "github";
  private static final String CONTINUOUS_INTEGRATION_GROUP_DOC = "Continuous Integration";

  private GitHubActionsModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Maven implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(GITHUB_ACTIONS_MAVEN)
        .withoutProperties()
        .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitHub Actions for Maven Build")
        .organization(Seed4JModuleOrganization.builder().feature(GITHUB_ACTIONS).addDependency(MAVEN_JAVA).build())
        .tags(CI_TAG, GITHUB_TAG)
        .factory(new GitHubActionsModuleFactory(services)::buildGitHubActionsMavenModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Gradle implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(GITHUB_ACTIONS_GRADLE)
        .withoutProperties()
        .apiDoc(CONTINUOUS_INTEGRATION_GROUP_DOC, "Add GitHub Actions for Gradle Build")
        .organization(Seed4JModuleOrganization.builder().feature(GITHUB_ACTIONS).addDependency(GRADLE_WRAPPER).build())
        .tags(CI_TAG, GITHUB_TAG)
        .factory(new GitHubActionsModuleFactory(services)::buildGitHubActionsGradleModule);
    }
  }
}
