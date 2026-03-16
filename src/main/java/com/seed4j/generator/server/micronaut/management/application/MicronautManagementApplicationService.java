package com.seed4j.generator.server.micronaut.management.application;

import com.seed4j.generator.server.micronaut.management.domain.MicronautManagementModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautManagementApplicationService {

  private final MicronautManagementModuleFactory factory;

  public MicronautManagementApplicationService() {
    this.factory = new MicronautManagementModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
