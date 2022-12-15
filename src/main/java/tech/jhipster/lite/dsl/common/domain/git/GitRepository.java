package tech.jhipster.lite.dsl.common.domain.git;

import tech.jhipster.lite.project.domain.ProjectPath;

public interface GitRepository {
  void init(ProjectPath folder);

  void commitAll(ProjectPath folder, GitCommitMessage message);

  default void commitAll(ProjectPath folder, String message) {
    commitAll(folder, new GitCommitMessage(message));
  }
}
