package tech.jhipster.lite.generator.server.springboot.database.jooq.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.jooq.domain.JooqModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JooqApplicationService {

  private final JooqModuleFactory jooq;

  public JooqApplicationService() {
    jooq = new JooqModuleFactory();
  }

  public JHipsterModule buildPostgreSQL(JHipsterModuleProperties properties) {
    return jooq.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(JHipsterModuleProperties properties) {
    return jooq.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(JHipsterModuleProperties properties) {
    return jooq.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(JHipsterModuleProperties properties) {
    return jooq.buildMySQL(properties);
  }
}
