package com.seed4j.generator.ci.github.actions.application;

import com.seed4j.generator.ci.github.actions.domain.GitHubActionsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitHubActionsApplicationService {

  private final GitHubActionsModuleFactory gitHubActions;

  public GitHubActionsApplicationService(NodeVersions nodeVersions) {
    gitHubActions = new GitHubActionsModuleFactory(nodeVersions);
  }

  public Seed4JModule buildGitHubActionsMavenModule(Seed4JModuleProperties properties) {
    return gitHubActions.buildGitHubActionsMavenModule(properties);
  }

  public Seed4JModule buildGitHubActionsGradleModule(Seed4JModuleProperties properties) {
    return gitHubActions.buildGitHubActionsGradleModule(properties);
  }
}
