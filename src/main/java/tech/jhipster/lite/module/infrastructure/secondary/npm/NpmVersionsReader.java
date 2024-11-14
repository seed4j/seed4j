package tech.jhipster.lite.module.infrastructure.secondary.npm;

import tech.jhipster.lite.module.domain.npm.NpmPackagesVersions;

/**
 * <p>
 * Read version for a npm dependency
 * </p>
 *
 * <p>
 * Spring beans instances of this interface will be used to resolve npm versions
 * </p>
 */
public interface NpmVersionsReader {
  /**
   * Get the npm packages versions from the given source
   *
   * @return The managed npm package versions
   */
  NpmPackagesVersions get();
}
