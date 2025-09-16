package com.seed4j.generator.client.tools.cypress.application;

import com.seed4j.generator.client.tools.cypress.domain.CypressModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CypressApplicationService {

  private final CypressModuleFactory cypress;

  public CypressApplicationService() {
    cypress = new CypressModuleFactory();
  }

  public Seed4JModule buildComponentTestsModule(Seed4JModuleProperties properties) {
    return cypress.buildComponentTestsModule(properties);
  }

  public Seed4JModule buildE2ETestsModule(Seed4JModuleProperties properties) {
    return cypress.buildE2ETestsModule(properties);
  }
}
