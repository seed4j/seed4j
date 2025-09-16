package com.seed4j.generator.ci.gitlab.application;

import com.seed4j.generator.ci.gitlab.domain.GitLabCiModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitLabCiApplicationService {

  private final GitLabCiModuleFactory gitLabCi;

  public GitLabCiApplicationService() {
    gitLabCi = new GitLabCiModuleFactory();
  }

  public Seed4JModule buildGitLabCiMavenModule(Seed4JModuleProperties properties) {
    return gitLabCi.buildGitLabCiMavenModule(properties);
  }

  public Seed4JModule buildGitLabCiGradleModule(Seed4JModuleProperties properties) {
    return gitLabCi.buildGitLabCiGradleModule(properties);
  }
}
