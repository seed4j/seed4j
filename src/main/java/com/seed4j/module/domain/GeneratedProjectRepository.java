package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;

public interface GeneratedProjectRepository {
  JHipsterProjectFilesPaths list(JHipsterProjectFolder folder, JHipsterFileMatcher files);
}
