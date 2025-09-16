package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.domain.SampleMongoDBPersistenceModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleMongoDBPersistenceApplicationService {

  private final SampleMongoDBPersistenceModuleFactory sampleMongoDBPersistence;

  public SampleMongoDBPersistenceApplicationService() {
    sampleMongoDBPersistence = new SampleMongoDBPersistenceModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleMongoDBPersistence.buildModule(properties);
  }
}
