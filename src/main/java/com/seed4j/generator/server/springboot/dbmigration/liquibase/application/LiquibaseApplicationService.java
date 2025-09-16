package com.seed4j.generator.server.springboot.dbmigration.liquibase.application;

import com.seed4j.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseModuleFactory liquibase;

  public LiquibaseApplicationService() {
    liquibase = new LiquibaseModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return liquibase.buildModule(properties);
  }

  public Seed4JModule buildAsyncModule(Seed4JModuleProperties properties) {
    return liquibase.buildAsyncModule(properties);
  }

  public Seed4JModule buildLinterModule(Seed4JModuleProperties properties) {
    return liquibase.buildLinterModule(properties);
  }
}
