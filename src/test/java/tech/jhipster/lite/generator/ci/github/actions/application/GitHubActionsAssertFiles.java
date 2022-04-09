package tech.jhipster.lite.generator.ci.github.actions.application;

import static tech.jhipster.lite.TestUtils.assertFileExist;

import tech.jhipster.lite.generator.tools.domain.Project;

public class GitHubActionsAssertFiles {

  public static void assertFilesYml(Project project) {
    assertFileExist(project, ".github/workflows/github-actions.yml");
    assertFileExist(project, ".github/actions/setup/action.yml");
  }

  public static void assertFilesGitHubActions(Project project) {
    assertFilesYml(project);
  }
}
