package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitLabCiModuleFactory {

  private static final JHipsterSource SOURCE = from("ci/gitlab");

  public JHipsterModule buildGitLabCiMavenModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template(".gitlab-ci-maven.yml.mustache"), to(".gitlab-ci.yml"))
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildGitLabCiGradleModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties).files().add(SOURCE.template(".gitlab-ci-gradle.yml.mustache"), to(".gitlab-ci.yml")).and().build();
  }
}
