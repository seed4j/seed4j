package com.seed4j.module.domain;

import com.seed4j.module.domain.properties.JHipsterModuleProperties;

@FunctionalInterface
public interface JHipsterModuleFactory {
  JHipsterModule create(JHipsterModuleProperties properties);
}
