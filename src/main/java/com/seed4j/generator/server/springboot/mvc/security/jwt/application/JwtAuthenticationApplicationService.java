package com.seed4j.generator.server.springboot.mvc.security.jwt.application;

import com.seed4j.generator.server.springboot.mvc.security.jwt.domain.JwtAuthenticationModuleFactory;
import com.seed4j.generator.server.springboot.mvc.security.jwt.domain.JwtBasicAuthModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationApplicationService {

  private final JwtAuthenticationModuleFactory authenticationFactory;
  private final JwtBasicAuthModuleFactory basicAuthFactory;

  public JwtAuthenticationApplicationService() {
    authenticationFactory = new JwtAuthenticationModuleFactory();
    basicAuthFactory = new JwtBasicAuthModuleFactory();
  }

  public JHipsterModule buildAuthenticationModule(SeedModuleProperties properties) {
    return authenticationFactory.buildModule(properties);
  }

  public JHipsterModule buildBasicAuthModule(SeedModuleProperties properties) {
    return basicAuthFactory.buildModule(properties);
  }
}
