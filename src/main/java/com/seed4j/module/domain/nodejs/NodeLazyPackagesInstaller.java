package com.seed4j.module.domain.nodejs;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;

/**
 * Install Node.js packages if a previous installation has already been done.
 */
public interface NodeLazyPackagesInstaller {
  void runInstallIn(JHipsterProjectFolder folder, NodePackageManager nodePackageManager);
}
