package tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.domain.Hibernate2ndLevelCacheModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class Hibernate2ndLevelCacheApplicationService {

  private final Hibernate2ndLevelCacheModuleFactory hibernate2ndLevelCache;

  public Hibernate2ndLevelCacheApplicationService() {
    hibernate2ndLevelCache = new Hibernate2ndLevelCacheModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return hibernate2ndLevelCache.buildModule(properties);
  }
}
