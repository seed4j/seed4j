package com.seed4j.generator.server.springboot.dbmigration.flyway.application;

import com.seed4j.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FlywayApplicationService {

  private final FlywayModuleFactory flyway;

  public FlywayApplicationService() {
    flyway = new FlywayModuleFactory();
  }

  public SeedModule buildInitializationModule(SeedModuleProperties properties) {
    return flyway.buildInitializationModule(properties);
  }

  public SeedModule buildMysqlDependencyModule(SeedModuleProperties properties) {
    return flyway.buildMysqlDependencyModule(properties);
  }

  public SeedModule buildPostgreSQLDependencyModule(SeedModuleProperties properties) {
    return flyway.buildPostgreSQLDependencyModule(properties);
  }

  public SeedModule buildMsSqlServerDependencyModule(SeedModuleProperties properties) {
    return flyway.buildMsSqlServerDependencyModule(properties);
  }
}
