package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JSlug;

public sealed interface Seed4JLandscapeDependency permits Seed4JFeatureDependency, Seed4JModuleDependency {
  Seed4JSlug slug();

  Seed4JLandscapeElementType type();
}
