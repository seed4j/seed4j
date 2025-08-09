package com.seed4j.generator.server.springboot.cache.ehcache.application;

import com.seed4j.generator.server.springboot.cache.ehcache.domain.EhcacheModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class EhcacheApplicationService {

  private final EhcacheModuleFactory ehCache;

  public EhcacheApplicationService() {
    ehCache = new EhcacheModuleFactory();
  }

  public SeedModule buildJavaConfigurationModule(SeedModuleProperties properties) {
    return ehCache.buildJavaConfigurationModule(properties);
  }

  public SeedModule buildXmlConfigurationModule(SeedModuleProperties properties) {
    return ehCache.buildXmlConfigurationModule(properties);
  }
}
