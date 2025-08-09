package com.seed4j.generator.server.springboot.cucumberauthentication.application;

import com.seed4j.generator.server.springboot.cucumberauthentication.domain.CucumberAuthenticationModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CucumberAuthenticationApplicationService {

  private final CucumberAuthenticationModuleFactory cucumberAuthentication;

  public CucumberAuthenticationApplicationService() {
    cucumberAuthentication = new CucumberAuthenticationModuleFactory();
  }

  public SeedModule buildOauth2Module(SeedModuleProperties properties) {
    return cucumberAuthentication.buildOauth2Module(properties);
  }

  public SeedModule buildJWTModule(SeedModuleProperties properties) {
    return cucumberAuthentication.buildJWTModule(properties);
  }
}
