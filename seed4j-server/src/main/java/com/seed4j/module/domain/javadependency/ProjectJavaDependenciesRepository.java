package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;

public interface ProjectJavaDependenciesRepository {
  ProjectJavaDependencies get(Seed4JProjectFolder folder);
}
