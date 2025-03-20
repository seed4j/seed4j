package tech.jhipster.lite.generator.server.springboot.cucumberauthentication.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.cucumberauthentication.domain.CucumberAuthenticationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CucumberAuthenticationApplicationService {

  private final CucumberAuthenticationModuleFactory cucumberAuthentication;

  public CucumberAuthenticationApplicationService() {
    cucumberAuthentication = new CucumberAuthenticationModuleFactory();
  }

  public JHipsterModule buildOauth2Module(JHipsterModuleProperties properties) {
    return cucumberAuthentication.buildOauth2Module(properties);
  }

  public JHipsterModule buildJWTModule(JHipsterModuleProperties properties) {
    return cucumberAuthentication.buildJWTModule(properties);
  }
}
