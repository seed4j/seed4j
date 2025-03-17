package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseModuleFactory liquibase;

  public LiquibaseApplicationService() {
    liquibase = new LiquibaseModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return liquibase.buildModule(properties);
  }

  public JHipsterModule buildAsyncModule(JHipsterModuleProperties properties) {
    return liquibase.buildAsyncModule(properties);
  }

  public JHipsterModule buildLinterModule(JHipsterModuleProperties properties) {
    return liquibase.buildLinterModule(properties);
  }
}
