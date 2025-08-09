package com.seed4j.generator.server.springboot.mvc.security.oauth2.account.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.account.domain.OAuth2AccountModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AccountApplicationService {

  private final OAuth2AccountModuleFactory oAuth2Account;

  public OAuth2AccountApplicationService() {
    oAuth2Account = new OAuth2AccountModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return oAuth2Account.buildModule(properties);
  }
}
