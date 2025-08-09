package com.seed4j.generator.server.springboot.cache.simple.application;

import com.seed4j.generator.server.springboot.cache.simple.domain.SimpleCacheModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SimpleCacheApplicationService {

  private final SimpleCacheModuleFactory simpleCache;

  public SimpleCacheApplicationService() {
    simpleCache = new SimpleCacheModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return simpleCache.buildModule(properties);
  }
}
