package tech.jhipster.lite.generator.client.react.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwtModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ReactJwtApplicationService {

  private final ReactJwtModuleFactory reactJwt;

  public ReactJwtApplicationService() {
    reactJwt = new ReactJwtModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return reactJwt.buildModule(properties);
  }
}
