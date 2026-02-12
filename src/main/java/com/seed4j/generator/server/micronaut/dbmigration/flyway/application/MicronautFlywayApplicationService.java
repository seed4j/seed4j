package com.seed4j.generator.server.micronaut.dbmigration.flyway.application;

import com.seed4j.generator.server.micronaut.dbmigration.flyway.domain.MicronautFlywayModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautFlywayApplicationService {

  private final MicronautFlywayModuleFactory factory;

  public MicronautFlywayApplicationService() {
    this.factory = new MicronautFlywayModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
