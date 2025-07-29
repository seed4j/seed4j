package com.seed4j.module.domain.git;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;

public interface GitRepository {
  void init(JHipsterProjectFolder folder);

  void commitAll(JHipsterProjectFolder folder, GitCommitMessage message);

  default void commitAll(JHipsterProjectFolder folder, String message) {
    commitAll(folder, new GitCommitMessage(message));
  }
}
