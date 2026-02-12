package com.seed4j.generator.server.micronaut.security.jwt.application;

import com.seed4j.generator.server.micronaut.security.jwt.domain.MicronautSecurityJwtModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautSecurityJwtApplicationService {

  private final MicronautSecurityJwtModuleFactory factory;

  public MicronautSecurityJwtApplicationService() {
    this.factory = new MicronautSecurityJwtModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
