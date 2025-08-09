package com.seed4j.generator.server.springboot.mvc.sample.flyway.application;

import com.seed4j.generator.server.springboot.mvc.sample.flyway.domain.SampleFlywayModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleFlywayApplicationService {

  private final SampleFlywayModuleFactory sampleFlyway;

  public SampleFlywayApplicationService() {
    sampleFlyway = new SampleFlywayModuleFactory();
  }

  public JHipsterModule buildPostgreSQLModule(SeedModuleProperties properties) {
    return sampleFlyway.buildPostgreSQLModule(properties);
  }

  public JHipsterModule buildNotPostgreSQLModule(SeedModuleProperties properties) {
    return sampleFlyway.buildNotPostgreSQLModule(properties);
  }
}
