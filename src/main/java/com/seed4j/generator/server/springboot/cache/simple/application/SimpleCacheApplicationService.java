package com.seed4j.generator.server.springboot.cache.simple.application;

import com.seed4j.generator.server.springboot.cache.simple.domain.SimpleCacheModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SimpleCacheApplicationService {

  private final SimpleCacheModuleFactory simpleCache;

  public SimpleCacheApplicationService() {
    simpleCache = new SimpleCacheModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return simpleCache.buildModule(properties);
  }
}
