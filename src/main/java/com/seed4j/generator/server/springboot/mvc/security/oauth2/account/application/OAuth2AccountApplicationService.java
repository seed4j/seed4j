package com.seed4j.generator.server.springboot.mvc.security.oauth2.account.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.account.domain.OAuth2AccountModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AccountApplicationService {

  private final OAuth2AccountModuleFactory oAuth2Account;

  public OAuth2AccountApplicationService() {
    oAuth2Account = new OAuth2AccountModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return oAuth2Account.buildModule(properties);
  }
}
