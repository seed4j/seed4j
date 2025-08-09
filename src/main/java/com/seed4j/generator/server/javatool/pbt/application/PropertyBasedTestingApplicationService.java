package com.seed4j.generator.server.javatool.pbt.application;

import com.seed4j.generator.server.javatool.pbt.domain.PropertyBasedTestingModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PropertyBasedTestingApplicationService {

  private final PropertyBasedTestingModuleFactory propertyBasedTesting;

  public PropertyBasedTestingApplicationService() {
    propertyBasedTesting = new PropertyBasedTestingModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return propertyBasedTesting.buildModule(properties);
  }
}
