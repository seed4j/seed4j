package com.seed4j.module.domain;

import com.seed4j.module.domain.landscape.Seed4JLandscape;
import com.seed4j.module.domain.resource.Seed4JModulesResources;

public interface Seed4JModulesRepository {
  Seed4JModulesResources resources();

  Seed4JLandscape landscape();

  /**
   * Perform updates to target project
   * Side effect: Write files to filesystem
   * @param changes to be written
   *
   */
  void apply(Seed4JModuleChanges changes);

  void applied(Seed4JModuleApplied moduleApplied);
}
