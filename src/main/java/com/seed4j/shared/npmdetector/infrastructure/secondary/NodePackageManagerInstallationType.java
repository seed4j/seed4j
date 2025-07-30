package com.seed4j.shared.npmdetector.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage(reason = "Cases can only be tested by using different computers")
public enum NodePackageManagerInstallationType {
  NONE,
  UNIX,
  WINDOWS,
}
