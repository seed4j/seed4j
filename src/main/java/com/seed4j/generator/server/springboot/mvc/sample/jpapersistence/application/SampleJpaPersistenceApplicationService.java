package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.domain.SampleJpaPersistenceModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleJpaPersistenceApplicationService {

  private final SampleJpaPersistenceModuleFactory sampleJpaPersistence;

  public SampleJpaPersistenceApplicationService() {
    sampleJpaPersistence = new SampleJpaPersistenceModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleJpaPersistence.buildModule(properties);
  }
}
