package com.seed4j.generator.server.springboot.cache.caffeine.application;

import com.seed4j.generator.server.springboot.cache.caffeine.domain.CaffeineCacheModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CaffeineCacheApplicationService {

  private final CaffeineCacheModuleFactory caffeineCache;

  public CaffeineCacheApplicationService() {
    caffeineCache = new CaffeineCacheModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return caffeineCache.buildModule(properties);
  }
}
