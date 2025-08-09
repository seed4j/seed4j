package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.SeedModuleProperties;

@FunctionalInterface
public interface JHipsterModuleFactory {
  JHipsterModule create(SeedModuleProperties properties);
}
