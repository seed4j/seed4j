package com.seed4j.generator.ci.renovate.application;

import com.seed4j.generator.ci.renovate.domain.RenovateModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RenovateApplicationService {

  private final RenovateModuleFactory renovate;

  public RenovateApplicationService() {
    renovate = new RenovateModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return renovate.buildModule(properties);
  }
}
