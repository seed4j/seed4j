package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.module.domain.properties.SeedProjectFolder;

/**
 * <p>
 * Read java dependencies in existing JHipster project folder.
 * </p>
 */
public interface JHipsterProjectFolderJavaDependenciesReader {
  ProjectJavaDependencies get(SeedProjectFolder folder);
}
