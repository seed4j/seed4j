package com.seed4j.module.domain.nodejs;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;

/**
 * Install Node.js packages if a previous installation has already been done.
 */
public interface NodeLazyPackagesInstaller {
  void runInstallIn(Seed4JProjectFolder folder, NodePackageManager nodePackageManager);
}
