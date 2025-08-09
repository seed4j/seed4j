package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.application;

import com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.domain.SampleCassandraPersistenceModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleCassandraPersistenceApplicationService {

  private final SampleCassandraPersistenceModuleFactory sampleCassandraPersistence;

  public SampleCassandraPersistenceApplicationService() {
    sampleCassandraPersistence = new SampleCassandraPersistenceModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return sampleCassandraPersistence.buildModule(properties);
  }
}
