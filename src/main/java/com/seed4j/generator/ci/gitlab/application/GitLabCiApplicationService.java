package com.seed4j.generator.ci.gitlab.application;

import com.seed4j.generator.ci.gitlab.domain.GitLabCiModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitLabCiApplicationService {

  private final GitLabCiModuleFactory gitLabCi;

  public GitLabCiApplicationService() {
    gitLabCi = new GitLabCiModuleFactory();
  }

  public SeedModule buildGitLabCiMavenModule(SeedModuleProperties properties) {
    return gitLabCi.buildGitLabCiMavenModule(properties);
  }

  public SeedModule buildGitLabCiGradleModule(SeedModuleProperties properties) {
    return gitLabCi.buildGitLabCiGradleModule(properties);
  }
}
