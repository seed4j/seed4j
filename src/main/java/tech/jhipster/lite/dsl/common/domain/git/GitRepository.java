package tech.jhipster.lite.module.domain.git;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public interface GitRepository {
  void init(JHipsterProjectFolder folder);

  void commitAll(JHipsterProjectFolder folder, GitCommitMessage message);

  default void commitAll(JHipsterProjectFolder folder, String message) {
    commitAll(folder, new GitCommitMessage(message));
  }
}
