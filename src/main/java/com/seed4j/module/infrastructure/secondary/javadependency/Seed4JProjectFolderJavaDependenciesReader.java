package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;

/**
 * <p>
 * Read java dependencies in existing Seed4J project folder.
 * </p>
 */
public interface Seed4JProjectFolderJavaDependenciesReader {
  ProjectJavaDependencies get(Seed4JProjectFolder folder);
}
