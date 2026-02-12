package com.seed4j.generator.server.micronaut.openapi.application;

import com.seed4j.generator.server.micronaut.openapi.domain.MicronautOpenApiModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautOpenApiApplicationService {

  private final MicronautOpenApiModuleFactory factory;

  public MicronautOpenApiApplicationService() {
    this.factory = new MicronautOpenApiModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
