package com.seed4j.module.domain.git;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;

public interface GitRepository {
  void init(Seed4JProjectFolder folder);

  void commitAll(Seed4JProjectFolder folder, GitCommitMessage message);

  default void commitAll(Seed4JProjectFolder folder, String message) {
    commitAll(folder, new GitCommitMessage(message));
  }
}
