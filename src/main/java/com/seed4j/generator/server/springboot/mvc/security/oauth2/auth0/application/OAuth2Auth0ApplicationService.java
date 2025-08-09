package com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.domain.OAuth2Auth0ModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Auth0ApplicationService {

  private final OAuth2Auth0ModuleFactory oAuth2Auth0;

  public OAuth2Auth0ApplicationService() {
    oAuth2Auth0 = new OAuth2Auth0ModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return oAuth2Auth0.buildModule(properties);
  }
}
