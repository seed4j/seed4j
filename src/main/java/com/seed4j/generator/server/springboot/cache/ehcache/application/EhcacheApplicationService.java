package com.seed4j.generator.server.springboot.cache.ehcache.application;

import com.seed4j.generator.server.springboot.cache.ehcache.domain.EhcacheModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class EhcacheApplicationService {

  private final EhcacheModuleFactory ehCache;

  public EhcacheApplicationService() {
    ehCache = new EhcacheModuleFactory();
  }

  public JHipsterModule buildJavaConfigurationModule(JHipsterModuleProperties properties) {
    return ehCache.buildJavaConfigurationModule(properties);
  }

  public JHipsterModule buildXmlConfigurationModule(JHipsterModuleProperties properties) {
    return ehCache.buildXmlConfigurationModule(properties);
  }
}
