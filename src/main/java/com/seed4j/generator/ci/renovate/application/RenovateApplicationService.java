package com.seed4j.generator.ci.renovate.application;

import com.seed4j.generator.ci.renovate.domain.RenovateModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RenovateApplicationService {

  private final RenovateModuleFactory renovate;

  public RenovateApplicationService() {
    renovate = new RenovateModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return renovate.buildModule(properties);
  }
}
