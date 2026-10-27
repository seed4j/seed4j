package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodeVersions;

/**
 * Services available to build Seed4J modules.
 */
public interface Seed4JModuleServices {
  /**
   * Docker images versions, to use in generated files.
   *
   * @return The docker images versions
   */
  DockerImages dockerImages();

  /**
   * Node.js packages versions, to use in generated files.
   *
   * @return The Node.js versions
   */
  NodeVersions nodeVersions();

  /**
   * Installs Node.js packages in the generated project, if they have already been installed.
   *
   * @return The Node.js lazy packages installer
   */
  NodeLazyPackagesInstaller nodeLazyPackagesInstaller();
}
