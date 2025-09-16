package com.seed4j.generator.server.springboot.cucumberauthentication.application;

import com.seed4j.generator.server.springboot.cucumberauthentication.domain.CucumberAuthenticationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CucumberAuthenticationApplicationService {

  private final CucumberAuthenticationModuleFactory cucumberAuthentication;

  public CucumberAuthenticationApplicationService() {
    cucumberAuthentication = new CucumberAuthenticationModuleFactory();
  }

  public Seed4JModule buildOauth2Module(Seed4JModuleProperties properties) {
    return cucumberAuthentication.buildOauth2Module(properties);
  }

  public Seed4JModule buildJWTModule(Seed4JModuleProperties properties) {
    return cucumberAuthentication.buildJWTModule(properties);
  }
}
