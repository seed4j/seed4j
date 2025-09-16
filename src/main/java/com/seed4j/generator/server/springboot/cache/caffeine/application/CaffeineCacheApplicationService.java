package com.seed4j.generator.server.springboot.cache.caffeine.application;

import com.seed4j.generator.server.springboot.cache.caffeine.domain.CaffeineCacheModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CaffeineCacheApplicationService {

  private final CaffeineCacheModuleFactory caffeineCache;

  public CaffeineCacheApplicationService() {
    caffeineCache = new CaffeineCacheModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return caffeineCache.buildModule(properties);
  }
}
