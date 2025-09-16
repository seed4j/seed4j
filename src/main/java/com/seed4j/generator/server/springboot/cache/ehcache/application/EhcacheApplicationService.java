package com.seed4j.generator.server.springboot.cache.ehcache.application;

import com.seed4j.generator.server.springboot.cache.ehcache.domain.EhcacheModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class EhcacheApplicationService {

  private final EhcacheModuleFactory ehCache;

  public EhcacheApplicationService() {
    ehCache = new EhcacheModuleFactory();
  }

  public Seed4JModule buildJavaConfigurationModule(Seed4JModuleProperties properties) {
    return ehCache.buildJavaConfigurationModule(properties);
  }

  public Seed4JModule buildXmlConfigurationModule(Seed4JModuleProperties properties) {
    return ehCache.buildXmlConfigurationModule(properties);
  }
}
