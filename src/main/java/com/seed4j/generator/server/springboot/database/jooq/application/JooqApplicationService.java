package com.seed4j.generator.server.springboot.database.jooq.application;

import com.seed4j.generator.server.springboot.database.jooq.domain.JooqModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JooqApplicationService {

  private final JooqModuleFactory jooq;

  public JooqApplicationService() {
    jooq = new JooqModuleFactory();
  }

  public JHipsterModule buildPostgreSQL(SeedModuleProperties properties) {
    return jooq.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(SeedModuleProperties properties) {
    return jooq.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(SeedModuleProperties properties) {
    return jooq.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(SeedModuleProperties properties) {
    return jooq.buildMySQL(properties);
  }
}
