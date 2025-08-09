package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.domain.SampleMongoDBPersistenceModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleMongoDBPersistenceApplicationService {

  private final SampleMongoDBPersistenceModuleFactory sampleMongoDBPersistence;

  public SampleMongoDBPersistenceApplicationService() {
    sampleMongoDBPersistence = new SampleMongoDBPersistenceModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return sampleMongoDBPersistence.buildModule(properties);
  }
}
