package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.properties.SeedProjectFolder;

public interface ProjectJavaDependenciesRepository {
  ProjectJavaDependencies get(SeedProjectFolder folder);
}
