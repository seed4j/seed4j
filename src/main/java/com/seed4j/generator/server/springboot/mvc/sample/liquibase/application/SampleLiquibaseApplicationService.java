package com.seed4j.generator.server.springboot.mvc.sample.liquibase.application;

import com.seed4j.generator.server.springboot.mvc.sample.liquibase.domain.SampleLiquibaseModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleLiquibaseApplicationService {

  private final SampleLiquibaseModuleFactory sampleLiquibase;

  public SampleLiquibaseApplicationService() {
    sampleLiquibase = new SampleLiquibaseModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return sampleLiquibase.buildModule(properties);
  }
}
