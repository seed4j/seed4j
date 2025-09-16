package com.seed4j.generator.server.springboot.mvc.sample.flyway.application;

import com.seed4j.generator.server.springboot.mvc.sample.flyway.domain.SampleFlywayModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleFlywayApplicationService {

  private final SampleFlywayModuleFactory sampleFlyway;

  public SampleFlywayApplicationService() {
    sampleFlyway = new SampleFlywayModuleFactory();
  }

  public Seed4JModule buildPostgreSQLModule(Seed4JModuleProperties properties) {
    return sampleFlyway.buildPostgreSQLModule(properties);
  }

  public Seed4JModule buildNotPostgreSQLModule(Seed4JModuleProperties properties) {
    return sampleFlyway.buildNotPostgreSQLModule(properties);
  }
}
