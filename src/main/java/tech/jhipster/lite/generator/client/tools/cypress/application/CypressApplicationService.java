package tech.jhipster.lite.generator.client.tools.cypress.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CypressApplicationService {

  private final CypressModuleFactory cypress;

  public CypressApplicationService() {
    cypress = new CypressModuleFactory();
  }

  public JHipsterModule buildComponentTestsModule(JHipsterModuleProperties properties) {
    return cypress.buildComponentTestsModule(properties);
  }

  public JHipsterModule buildE2ETestsModule(JHipsterModuleProperties properties) {
    return cypress.buildE2ETestsModule(properties);
  }
}
