package com.seed4j.generator.server.springboot.database.jooq.application;

import com.seed4j.generator.server.springboot.database.jooq.domain.JooqModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
