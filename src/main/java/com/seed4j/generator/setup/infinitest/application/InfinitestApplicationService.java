package com.seed4j.generator.setup.infinitest.application;

import com.seed4j.generator.setup.infinitest.domain.InfinitestModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class InfinitestApplicationService {

  private final InfinitestModuleFactory infinitest;

  public InfinitestApplicationService() {
    infinitest = new InfinitestModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return infinitest.buildModule(properties);
  }
}
