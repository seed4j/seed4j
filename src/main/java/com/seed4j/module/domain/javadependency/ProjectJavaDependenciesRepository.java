package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;

public interface ProjectJavaDependenciesRepository {
  ProjectJavaDependencies get(JHipsterProjectFolder folder);
}
