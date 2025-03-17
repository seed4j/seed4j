package tech.jhipster.lite.generator.server.springboot.mvc.sample.cassandrapersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.cassandrapersistence.domain.SampleCassandraPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SampleCassandraPersistenceApplicationService {

  private final SampleCassandraPersistenceModuleFactory sampleCassandraPersistence;

  public SampleCassandraPersistenceApplicationService() {
    sampleCassandraPersistence = new SampleCassandraPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return sampleCassandraPersistence.buildModule(properties);
  }
}
