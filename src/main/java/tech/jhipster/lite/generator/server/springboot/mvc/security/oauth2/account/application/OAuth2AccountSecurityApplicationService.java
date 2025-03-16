package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.account.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.account.domain.OAuth2AccountModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2AccountSecurityApplicationService {

  private final OAuth2AccountModuleFactory factory;

  public OAuth2AccountSecurityApplicationService() {
    factory = new OAuth2AccountModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
