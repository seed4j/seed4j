package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopNodeLazyPackagesInstaller implements NodeLazyPackagesInstaller {

  private static final Logger log = LoggerFactory.getLogger(NoopNodeLazyPackagesInstaller.class);

  @Override
  public void runInstallIn(SeedProjectFolder folder, NodePackageManager nodePackageManager) {
    log.info("Simulating installation of Node.js dependencies");
  }
}
