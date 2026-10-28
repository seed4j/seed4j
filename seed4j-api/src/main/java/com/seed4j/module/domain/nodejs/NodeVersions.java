package com.seed4j.module.domain.nodejs;

/**
 * Get Node.js dependencies versions
 */
public interface NodeVersions {
  /**
   * Get managed Node.js package versions
   *
   * @return The managed Node.js version
   */
  NodePackagesVersions get();

  /**
   * Get the Node.js package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNodePackageException
   *           if the package can't be found in source
   */
  default NodePackageVersion get(NodePackageName packageName, NodePackagesVersionSource source) {
    return get().get(packageName, source);
  }

  /**
   * Get the Node.js package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNodePackageException
   *           if the package can't be found in source
   */
  default NodePackageVersion get(String packageName, NodePackagesVersionSource source) {
    return get(new NodePackageName(packageName), source);
  }

  /**
   * Get the Node.js package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNodePackageException
   *           if the package can't be found in source
   */
  default NodePackageVersion get(String packageName, NodePackagesVersionSourceFactory source) {
    return get(packageName, source.build());
  }

  /**
   * The version of Node.js.
   */
  default NodePackageVersion nodeVersion() {
    return get("node", Seed4JNodePackagesVersionSource.COMMON.build());
  }

  default NodePackageVersion packageManagerVersion(NodePackageManager nodePackageManager) {
    return get(nodePackageManager.packageName(), Seed4JNodePackagesVersionSource.COMMON.build());
  }
}
