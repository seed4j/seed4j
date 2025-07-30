package com.seed4j.generator.server.springboot.dbmigration.flyway.application;

import com.seed4j.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FlywayApplicationService {

  private final FlywayModuleFactory flyway;

  public FlywayApplicationService() {
    flyway = new FlywayModuleFactory();
  }

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    return flyway.buildInitializationModule(properties);
  }

  public JHipsterModule buildMysqlDependencyModule(JHipsterModuleProperties properties) {
    return flyway.buildMysqlDependencyModule(properties);
  }

  public JHipsterModule buildPostgreSQLDependencyModule(JHipsterModuleProperties properties) {
    return flyway.buildPostgreSQLDependencyModule(properties);
  }

  public JHipsterModule buildMsSqlServerDependencyModule(JHipsterModuleProperties properties) {
    return flyway.buildMsSqlServerDependencyModule(properties);
  }
}
