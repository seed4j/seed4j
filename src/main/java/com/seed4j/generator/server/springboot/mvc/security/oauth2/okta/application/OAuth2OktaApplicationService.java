package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.domain.OAuth2OktaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2OktaApplicationService {

  private final OAuth2OktaModuleFactory oAuth2Okta;

  public OAuth2OktaApplicationService() {
    oAuth2Okta = new OAuth2OktaModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return oAuth2Okta.buildModule(properties);
  }
}
