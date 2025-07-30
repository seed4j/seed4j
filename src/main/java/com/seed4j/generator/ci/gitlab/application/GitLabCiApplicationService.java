package com.seed4j.generator.ci.gitlab.application;

import com.seed4j.generator.ci.gitlab.domain.GitLabCiModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitLabCiApplicationService {

  private final GitLabCiModuleFactory gitLabCi;

  public GitLabCiApplicationService() {
    gitLabCi = new GitLabCiModuleFactory();
  }

  public JHipsterModule buildGitLabCiMavenModule(JHipsterModuleProperties properties) {
    return gitLabCi.buildGitLabCiMavenModule(properties);
  }

  public JHipsterModule buildGitLabCiGradleModule(JHipsterModuleProperties properties) {
    return gitLabCi.buildGitLabCiGradleModule(properties);
  }
}
