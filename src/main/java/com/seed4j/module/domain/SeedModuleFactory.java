package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;

@FunctionalInterface
public interface SeedModuleFactory {
  SeedModule create(SeedModuleProperties properties);
}
