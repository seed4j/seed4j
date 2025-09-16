package com.seed4j.generator.server.springboot.mvc.sample.liquibase.application;

import com.seed4j.generator.server.springboot.mvc.sample.liquibase.domain.SampleLiquibaseModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleLiquibaseApplicationService {

  private final SampleLiquibaseModuleFactory sampleLiquibase;

  public SampleLiquibaseApplicationService() {
    sampleLiquibase = new SampleLiquibaseModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleLiquibase.buildModule(properties);
  }
}
