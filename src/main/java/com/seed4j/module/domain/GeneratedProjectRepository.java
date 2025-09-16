package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;

public interface GeneratedProjectRepository {
  Seed4JProjectFilesPaths list(Seed4JProjectFolder folder, Seed4JFileMatcher files);
}
