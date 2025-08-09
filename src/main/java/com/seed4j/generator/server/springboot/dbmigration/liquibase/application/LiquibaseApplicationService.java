package com.seed4j.generator.server.springboot.dbmigration.liquibase.application;

import com.seed4j.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseModuleFactory liquibase;

  public LiquibaseApplicationService() {
    liquibase = new LiquibaseModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return liquibase.buildModule(properties);
  }

  public JHipsterModule buildAsyncModule(SeedModuleProperties properties) {
    return liquibase.buildAsyncModule(properties);
  }

  public JHipsterModule buildLinterModule(SeedModuleProperties properties) {
    return liquibase.buildLinterModule(properties);
  }
}
