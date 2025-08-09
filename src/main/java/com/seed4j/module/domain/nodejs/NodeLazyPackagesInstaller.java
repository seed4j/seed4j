package com.seed4j.module.domain.nodejs;

import com.seed4j.module.domain.properties.SeedProjectFolder;

/**
 * Install Node.js packages if a previous installation has already been done.
 */
public interface NodeLazyPackagesInstaller {
  void runInstallIn(SeedProjectFolder folder, NodePackageManager nodePackageManager);
}
