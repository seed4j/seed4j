package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitLabCiModuleFactory {

  private static final SeedSource SOURCE = from("ci/gitlab");

  public SeedModule buildGitLabCiMavenModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template(".gitlab-ci-maven.yml.mustache"), to(".gitlab-ci.yml"))
        .and()
      .build();
    // @formatter:on
  }

  public SeedModule buildGitLabCiGradleModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties).files().add(SOURCE.template(".gitlab-ci-gradle.yml.mustache"), to(".gitlab-ci.yml")).and().build();
  }
}
