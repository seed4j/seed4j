package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.Seed4JModuleProperties;

@FunctionalInterface
public interface Seed4JModuleFactory {
  Seed4JModule create(Seed4JModuleProperties properties);
}
