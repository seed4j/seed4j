package tech.jhipster.lite.generator.ci.github.actions.application;

import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class GitHubActionsApplicationServiceIT {

  @Autowired
  GitHubActionsApplicationService gitHubActionsApplicationService;

  @Test
  void shouldAddGitHubActionsForMaven() {
    Project project = tmpProject();

    gitHubActionsApplicationService.addGitHubActionsForMaven(project);

    GitHubActionsAssertFiles.assertFilesYml(project);
  }
}
