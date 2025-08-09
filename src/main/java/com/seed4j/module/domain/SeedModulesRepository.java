package com.seed4j.module.domain;

import com.seed4j.module.domain.landscape.SeedLandscape;
import com.seed4j.module.domain.resource.SeedModulesResources;

public interface SeedModulesRepository {
  SeedModulesResources resources();

  SeedLandscape landscape();

  /**
   * Perform updates to target project
   * Side effect: Write files to filesystem
   * @param changes to be written
   *
   */
  void apply(SeedModuleChanges changes);

  void applied(SeedModuleApplied moduleApplied);
}
