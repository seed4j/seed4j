package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitLabCiModuleFactory {

  private static final Seed4JSource SOURCE = from("ci/gitlab");

  public Seed4JModule buildGitLabCiMavenModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template(".gitlab-ci-maven.yml.mustache"), to(".gitlab-ci.yml"))
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildGitLabCiGradleModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties).files().add(SOURCE.template(".gitlab-ci-gradle.yml.mustache"), to(".gitlab-ci.yml")).and().build();
  }
}
