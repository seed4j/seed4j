package tech.jhipster.lite.generator.client.angular.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.security.jwt.domain.AngularJwtModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class AngularJwtApplicationService {

  private final AngularJwtModuleFactory factory;

  public AngularJwtApplicationService() {
    factory = new AngularJwtModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
