package com.seed4j.generator.client.react.security.jwt.application;

import com.seed4j.generator.client.react.security.jwt.domain.ReactJwtModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ReactJwtApplicationService {

  private final ReactJwtModuleFactory reactJwt;

  public ReactJwtApplicationService() {
    reactJwt = new ReactJwtModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return reactJwt.buildModule(properties);
  }
}
