package com.seed4j.generator.client.angular.security.jwt.application;

import com.seed4j.generator.client.angular.security.jwt.domain.AngularJwtModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularJwtApplicationService {

  private final AngularJwtModuleFactory angularJwt;

  public AngularJwtApplicationService() {
    angularJwt = new AngularJwtModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return angularJwt.buildModule(properties);
  }
}
