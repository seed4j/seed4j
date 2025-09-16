package com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.domain.OAuth2Auth0ModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Auth0ApplicationService {

  private final OAuth2Auth0ModuleFactory oAuth2Auth0;

  public OAuth2Auth0ApplicationService() {
    oAuth2Auth0 = new OAuth2Auth0ModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return oAuth2Auth0.buildModule(properties);
  }
}
