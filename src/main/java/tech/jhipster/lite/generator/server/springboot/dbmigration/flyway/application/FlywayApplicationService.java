package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
