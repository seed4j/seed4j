package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.domain.OAuth2OktaModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2OktaApplicationService {

  private final OAuth2OktaModuleFactory oAuth2Okta;

  public OAuth2OktaApplicationService() {
    oAuth2Okta = new OAuth2OktaModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return oAuth2Okta.buildModule(properties);
  }
}
