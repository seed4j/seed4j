package tech.jhipster.lite.generator.server.springboot.cucumber_authentication.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.cucumber_authentication.domain.CucumberAuthenticationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CucumberAuthenticationApplicationService {

  private final CucumberAuthenticationModuleFactory factory;

  public CucumberAuthenticationApplicationService() {
    factory = new CucumberAuthenticationModuleFactory();
  }

  public JHipsterModule buildOauth2Module(JHipsterModuleProperties properties) {
    return factory.buildOauth2Module(properties);
  }

  public JHipsterModule buildJWTModule(JHipsterModuleProperties properties) {
    return factory.buildJWTModule(properties);
  }
}
