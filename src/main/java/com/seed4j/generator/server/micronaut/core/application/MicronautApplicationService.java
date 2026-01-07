package com.seed4j.generator.server.micronaut.core.application;

import com.seed4j.generator.server.micronaut.core.domain.MicronautCoreModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautApplicationService {

  private final MicronautCoreModuleFactory factory;

  public MicronautApplicationService() {
    factory = new MicronautCoreModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
