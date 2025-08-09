package com.seed4j.generator.client.angular.security.oauth2keycloak.application;

import com.seed4j.generator.client.angular.security.oauth2keycloak.domain.AngularOAuth2KeycloakModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularOAuth2KeycloakApplicationService {

  private final AngularOAuth2KeycloakModuleFactory angularOauth2;

  public AngularOAuth2KeycloakApplicationService() {
    angularOauth2 = new AngularOAuth2KeycloakModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return angularOauth2.buildModule(properties);
  }
}
