package com.seed4j.generator.client.angular.security.jwt.application;

import com.seed4j.generator.client.angular.security.jwt.domain.AngularJwtModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularJwtApplicationService {

  private final AngularJwtModuleFactory angularJwt;

  public AngularJwtApplicationService() {
    angularJwt = new AngularJwtModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return angularJwt.buildModule(properties);
  }
}
