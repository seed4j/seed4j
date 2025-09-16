package com.seed4j.generator.server.springboot.cucumber.application;

import com.seed4j.generator.server.springboot.cucumber.domain.CucumberModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CucumberApplicationService {

  private final CucumberModuleFactory cucumber;

  public CucumberApplicationService() {
    cucumber = new CucumberModuleFactory();
  }

  public Seed4JModule buildInitializationModule(Seed4JModuleProperties properties) {
    return cucumber.buildInitializationModule(properties);
  }

  public Seed4JModule buildJpaResetModule(Seed4JModuleProperties properties) {
    return cucumber.buildJpaResetModule(properties);
  }
}
