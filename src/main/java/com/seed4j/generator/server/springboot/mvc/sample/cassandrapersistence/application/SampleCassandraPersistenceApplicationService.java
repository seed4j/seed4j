package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.domain.SampleCassandraPersistenceModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleCassandraPersistenceApplicationService {

  private final SampleCassandraPersistenceModuleFactory sampleCassandraPersistence;

  public SampleCassandraPersistenceApplicationService() {
    sampleCassandraPersistence = new SampleCassandraPersistenceModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleCassandraPersistence.buildModule(properties);
  }
}
