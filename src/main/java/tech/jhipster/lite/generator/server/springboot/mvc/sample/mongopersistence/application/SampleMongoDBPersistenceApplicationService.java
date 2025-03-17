package tech.jhipster.lite.generator.server.springboot.mvc.sample.mongopersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.mongopersistence.domain.SampleMongoDBPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SampleMongoDBPersistenceApplicationService {

  private final SampleMongoDBPersistenceModuleFactory sampleMongoDBPersistence;

  public SampleMongoDBPersistenceApplicationService() {
    sampleMongoDBPersistence = new SampleMongoDBPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return sampleMongoDBPersistence.buildModule(properties);
  }
}
