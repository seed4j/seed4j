package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.domain.SampleJpaPersistenceModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
