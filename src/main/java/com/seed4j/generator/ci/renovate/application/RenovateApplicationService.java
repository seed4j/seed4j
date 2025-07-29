package com.seed4j.generator.ci.renovate.application;

import com.seed4j.generator.ci.renovate.domain.RenovateModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RenovateApplicationService {

  private final RenovateModuleFactory renovate;

  public RenovateApplicationService() {
    renovate = new RenovateModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return renovate.buildModule(properties);
  }
}
