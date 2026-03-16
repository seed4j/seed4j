package com.seed4j.generator.server.micronaut.cache.application;

import com.seed4j.generator.server.micronaut.cache.domain.MicronautCacheModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautCacheApplicationService {

  private final MicronautCacheModuleFactory factory;

  public MicronautCacheApplicationService() {
    this.factory = new MicronautCacheModuleFactory();
  }

  public Seed4JModule buildCaffeineModule(Seed4JModuleProperties properties) {
    return factory.buildCaffeineModule(properties);
  }

  public Seed4JModule buildRedisModule(Seed4JModuleProperties properties) {
    return factory.buildRedisModule(properties);
  }
}
