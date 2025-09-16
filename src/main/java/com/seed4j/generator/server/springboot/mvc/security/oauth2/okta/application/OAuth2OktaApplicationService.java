package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.domain.OAuth2OktaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2OktaApplicationService {

  private final OAuth2OktaModuleFactory oAuth2Okta;

  public OAuth2OktaApplicationService() {
    oAuth2Okta = new OAuth2OktaModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return oAuth2Okta.buildModule(properties);
  }
}
