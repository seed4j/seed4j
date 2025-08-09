package com.seed4j.generator.server.springboot.cucumber.application;

import com.seed4j.generator.server.springboot.cucumber.domain.CucumberModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CucumberApplicationService {

  private final CucumberModuleFactory cucumber;

  public CucumberApplicationService() {
    cucumber = new CucumberModuleFactory();
  }

  public JHipsterModule buildInitializationModule(SeedModuleProperties properties) {
    return cucumber.buildInitializationModule(properties);
  }

  public JHipsterModule buildJpaResetModule(SeedModuleProperties properties) {
    return cucumber.buildJpaResetModule(properties);
  }
}
