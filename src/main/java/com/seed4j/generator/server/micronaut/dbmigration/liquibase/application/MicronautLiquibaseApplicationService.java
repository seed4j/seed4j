package com.seed4j.generator.server.micronaut.dbmigration.liquibase.application;

import com.seed4j.generator.server.micronaut.dbmigration.liquibase.domain.MicronautLiquibaseModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautLiquibaseApplicationService {

  private final MicronautLiquibaseModuleFactory factory;

  public MicronautLiquibaseApplicationService() {
    this.factory = new MicronautLiquibaseModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
