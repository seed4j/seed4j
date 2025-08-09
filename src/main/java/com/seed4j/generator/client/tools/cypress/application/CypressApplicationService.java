package com.seed4j.generator.client.tools.cypress.application;

import com.seed4j.generator.client.tools.cypress.domain.CypressModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CypressApplicationService {

  private final CypressModuleFactory cypress;

  public CypressApplicationService() {
    cypress = new CypressModuleFactory();
  }

  public JHipsterModule buildComponentTestsModule(SeedModuleProperties properties) {
    return cypress.buildComponentTestsModule(properties);
  }

  public JHipsterModule buildE2ETestsModule(SeedModuleProperties properties) {
    return cypress.buildE2ETestsModule(properties);
  }
}
