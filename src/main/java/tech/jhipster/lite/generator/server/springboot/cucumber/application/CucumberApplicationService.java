package tech.jhipster.lite.generator.server.springboot.cucumber.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.cucumber.domain.CucumberModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CucumberApplicationService {

  private final CucumberModuleFactory cucumber;

  public CucumberApplicationService() {
    cucumber = new CucumberModuleFactory();
  }

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    return cucumber.buildInitializationModule(properties);
  }

  public JHipsterModule buildJpaResetModule(JHipsterModuleProperties properties) {
    return cucumber.buildJpaResetModule(properties);
  }
}
