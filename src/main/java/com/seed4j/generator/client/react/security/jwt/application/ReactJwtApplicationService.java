package com.seed4j.generator.client.react.security.jwt.application;

import com.seed4j.generator.client.react.security.jwt.domain.ReactJwtModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ReactJwtApplicationService {

  private final ReactJwtModuleFactory reactJwt;

  public ReactJwtApplicationService() {
    reactJwt = new ReactJwtModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return reactJwt.buildModule(properties);
  }
}
