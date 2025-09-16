package com.seed4j.generator.setup.infinitest.application;

import com.seed4j.generator.setup.infinitest.domain.InfinitestModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class InfinitestApplicationService {

  private final InfinitestModuleFactory infinitest;

  public InfinitestApplicationService() {
    infinitest = new InfinitestModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return infinitest.buildModule(properties);
  }
}
