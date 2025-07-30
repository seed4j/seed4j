package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterSlug;

public sealed interface JHipsterLandscapeDependency permits JHipsterFeatureDependency, JHipsterModuleDependency {
  JHipsterSlug slug();

  JHipsterLandscapeElementType type();
}
