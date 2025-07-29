package com.seed4j.generator.ci.github.actions.application;

import com.seed4j.generator.ci.github.actions.domain.GitHubActionsModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitHubActionsApplicationService {

  private final GitHubActionsModuleFactory gitHubActions;

  public GitHubActionsApplicationService(NodeVersions nodeVersions) {
    gitHubActions = new GitHubActionsModuleFactory(nodeVersions);
  }

  public JHipsterModule buildGitHubActionsMavenModule(JHipsterModuleProperties properties) {
    return gitHubActions.buildGitHubActionsMavenModule(properties);
  }

  public JHipsterModule buildGitHubActionsGradleModule(JHipsterModuleProperties properties) {
    return gitHubActions.buildGitHubActionsGradleModule(properties);
  }
}
