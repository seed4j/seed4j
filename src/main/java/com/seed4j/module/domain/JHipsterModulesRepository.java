package com.seed4j.module.domain;

import com.seed4j.module.domain.landscape.JHipsterLandscape;
import com.seed4j.module.domain.resource.JHipsterModulesResources;

public interface JHipsterModulesRepository {
  JHipsterModulesResources resources();

  JHipsterLandscape landscape();

  /**
   * Perform updates to target project
   * Side effect: Write files to filesystem
   * @param changes to be written
   *
   */
  void apply(JHipsterModuleChanges changes);

  void applied(JHipsterModuleApplied moduleApplied);
}
