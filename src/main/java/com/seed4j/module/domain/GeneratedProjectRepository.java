package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedProjectFolder;

public interface GeneratedProjectRepository {
  JHipsterProjectFilesPaths list(SeedProjectFolder folder, JHipsterFileMatcher files);
}
