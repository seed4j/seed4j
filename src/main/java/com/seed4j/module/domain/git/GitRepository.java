package com.seed4j.module.domain.git;

import com.seed4j.module.domain.properties.SeedProjectFolder;

public interface GitRepository {
  void init(SeedProjectFolder folder);

  void commitAll(SeedProjectFolder folder, GitCommitMessage message);

  default void commitAll(SeedProjectFolder folder, String message) {
    commitAll(folder, new GitCommitMessage(message));
  }
}
