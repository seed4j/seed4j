package tech.jhipster.lite.generator.client.angular.security.oauth2.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class AngularOauth2ApplicationService {

  private final AngularOauth2ModuleFactory angularOauth2;

  public AngularOauth2ApplicationService() {
    angularOauth2 = new AngularOauth2ModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return angularOauth2.buildModule(properties);
  }
}
