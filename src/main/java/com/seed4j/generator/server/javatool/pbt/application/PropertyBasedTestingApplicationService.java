package com.seed4j.generator.server.javatool.pbt.application;

import com.seed4j.generator.server.javatool.pbt.domain.PropertyBasedTestingModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PropertyBasedTestingApplicationService {

  private final PropertyBasedTestingModuleFactory propertyBasedTesting;

  public PropertyBasedTestingApplicationService() {
    propertyBasedTesting = new PropertyBasedTestingModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return propertyBasedTesting.buildModule(properties);
  }
}
