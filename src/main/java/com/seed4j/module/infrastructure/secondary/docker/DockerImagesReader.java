package com.seed4j.module.infrastructure.secondary.docker;

import com.seed4j.module.domain.docker.DockerImageVersions;

/**
 * <p>
 * Read information for a docker image.
 * </p>
 *
 * <p>
 * Spring beans instances of this interface are used to resolve docker images information.
 * </p>
 */
public interface DockerImagesReader {
  /**
   * Get docker images versions
   *
   * @return The docker images versions handled by this reader
   */
  DockerImageVersions get();
}
