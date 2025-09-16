package com.seed4j.generator.server.springboot.mvc.security.jwt.application;

import com.seed4j.generator.server.springboot.mvc.security.jwt.domain.JwtAuthenticationModuleFactory;
import com.seed4j.generator.server.springboot.mvc.security.jwt.domain.JwtBasicAuthModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationApplicationService {

  private final JwtAuthenticationModuleFactory authenticationFactory;
  private final JwtBasicAuthModuleFactory basicAuthFactory;

  public JwtAuthenticationApplicationService() {
    authenticationFactory = new JwtAuthenticationModuleFactory();
    basicAuthFactory = new JwtBasicAuthModuleFactory();
  }

  public Seed4JModule buildAuthenticationModule(Seed4JModuleProperties properties) {
    return authenticationFactory.buildModule(properties);
  }

  public Seed4JModule buildBasicAuthModule(Seed4JModuleProperties properties) {
    return basicAuthFactory.buildModule(properties);
  }
}
