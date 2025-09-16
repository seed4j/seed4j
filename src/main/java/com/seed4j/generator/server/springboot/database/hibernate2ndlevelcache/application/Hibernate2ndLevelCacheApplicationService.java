package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.application;

import com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.domain.Hibernate2ndLevelCacheModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class Hibernate2ndLevelCacheApplicationService {

  private final Hibernate2ndLevelCacheModuleFactory hibernate2ndLevelCache;

  public Hibernate2ndLevelCacheApplicationService() {
    hibernate2ndLevelCache = new Hibernate2ndLevelCacheModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return hibernate2ndLevelCache.buildModule(properties);
  }
}
