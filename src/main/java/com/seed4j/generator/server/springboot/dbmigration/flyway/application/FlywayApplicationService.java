package com.seed4j.generator.server.springboot.dbmigration.flyway.application;

import com.seed4j.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FlywayApplicationService {

  private final FlywayModuleFactory flyway;

  public FlywayApplicationService() {
    flyway = new FlywayModuleFactory();
  }

  public Seed4JModule buildInitializationModule(Seed4JModuleProperties properties) {
    return flyway.buildInitializationModule(properties);
  }

  public Seed4JModule buildMysqlDependencyModule(Seed4JModuleProperties properties) {
    return flyway.buildMysqlDependencyModule(properties);
  }

  public Seed4JModule buildPostgreSQLDependencyModule(Seed4JModuleProperties properties) {
    return flyway.buildPostgreSQLDependencyModule(properties);
  }

  public Seed4JModule buildMsSqlServerDependencyModule(Seed4JModuleProperties properties) {
    return flyway.buildMsSqlServerDependencyModule(properties);
  }
}
