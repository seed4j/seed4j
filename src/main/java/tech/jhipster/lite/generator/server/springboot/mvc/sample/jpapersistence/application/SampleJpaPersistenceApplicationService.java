package tech.jhipster.lite.generator.server.springboot.mvc.sample.jpapersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.jpapersistence.domain.SampleJpaPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SampleJpaPersistenceApplicationService {

  private final SampleJpaPersistenceModuleFactory sampleJpaPersistence;

  public SampleJpaPersistenceApplicationService() {
    sampleJpaPersistence = new SampleJpaPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return sampleJpaPersistence.buildModule(properties);
  }
}
