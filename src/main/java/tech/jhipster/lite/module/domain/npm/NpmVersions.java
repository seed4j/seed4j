package tech.jhipster.lite.module.domain.npm;

/**
 * Get NPM dependencies versions
 */
public interface NpmVersions {
  /**
   * Get managed NPM package versions
   *
   * @return The managed npm version
   */
  NpmPackagesVersions get();

  /**
   * Get the npm package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNpmPackageException
   *           is the package can't be found in source
   */
  default NpmPackageVersion get(NpmPackageName packageName, NpmVersionSource source) {
    return get().get(packageName, source);
  }

  /**
   * Get the npm package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNpmPackageException
   *           is the package can't be found in source
   */
  default NpmPackageVersion get(String packageName, NpmVersionSource source) {
    return get(new NpmPackageName(packageName), source);
  }

  /**
   * Get the npm package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNpmPackageException
   *           is the package can't be found in source
   */
  default NpmPackageVersion get(String packageName, NpmVersionSourceFactory source) {
    return get(packageName, source.build());
  }

  /**
   * @return The version of Node.js.
   */
  default NpmPackageVersion nodeVersion() {
    return get("node", JHLiteNpmVersionSource.COMMON.build());
  }
}
