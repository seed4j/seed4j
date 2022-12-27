package tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.domain.DummyCassandraPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DummyCassandraPersistenceApplicationService {

  private final DummyCassandraPersistenceModuleFactory factory;

  public DummyCassandraPersistenceApplicationService() {
    factory = new DummyCassandraPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
