package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;

/**
 * <p>
 * Read information for a java dependencies.
 * </p>
 *
 * <p>
 * Spring beans instances of this interface are used to resolve java dependencies information.
 * </p>
 */
public interface JavaDependenciesReader {
  /**
   * Get java dependencies
   *
   * @return The java dependencies handled by this reader
   */
  JavaDependenciesVersions get();
}
