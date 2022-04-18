package tech.jhipster.lite.generator.ci.github.actions.domain;

import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record GitHubActionsDomainService(ProjectRepository projectRepository) implements GitHubActionsService {
  public static final String GITHUB_ACTIONS_CI_SOURCE_FOLDER = "ci/github/actions/.github/workflows/";
  public static final String GITHUB_ACTIONS_MAVEN_CI_YML = "github-actions-maven.yml.mustache";
  public static final String GITHUB_ACTIONS_GRADLE_CI_YML = "github-actions-gradle.yml.mustache";
  public static final String GITHUB_ACTIONS_CI_YML = "github-actions.yml";
  public static final String GITHUB_ACTIONS_CI_DESTINATION_FOLDER = ".github/workflows/";

  public static final String GITHUB_ACTIONS_SETUP_SOURCE_FOLDER = "ci/github/actions/.github/actions/setup/";
  public static final String GITHUB_ACTIONS_SETUP_YML = "action.yml.mustache";
  private static final String GITHUB_ACTIONS_SETUP_DESTINATION_FOLDER = ".github/actions/setup/";

  @Override
  public void addGitHubActions(Project project) {
    addSetupYamls(project);
    if (project.isMavenProject()) {
      projectRepository.template(
        project,
        GITHUB_ACTIONS_CI_SOURCE_FOLDER,
        GITHUB_ACTIONS_MAVEN_CI_YML,
        GITHUB_ACTIONS_CI_DESTINATION_FOLDER,
        GITHUB_ACTIONS_CI_YML
      );
    }
    if (project.isGradleProject()) {
      projectRepository.template(
        project,
        GITHUB_ACTIONS_CI_SOURCE_FOLDER,
        GITHUB_ACTIONS_GRADLE_CI_YML,
        GITHUB_ACTIONS_CI_DESTINATION_FOLDER,
        GITHUB_ACTIONS_CI_YML
      );
    }
  }

  private void addSetupYamls(Project project) {
    projectRepository.template(
      project,
      GITHUB_ACTIONS_SETUP_SOURCE_FOLDER,
      GITHUB_ACTIONS_SETUP_YML,
      GITHUB_ACTIONS_SETUP_DESTINATION_FOLDER
    );
  }
}
