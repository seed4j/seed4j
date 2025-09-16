package com.seed4j.generator.server.springboot.database.jooq.application;

import com.seed4j.generator.server.springboot.database.jooq.domain.JooqModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JooqApplicationService {

  private final JooqModuleFactory jooq;

  public JooqApplicationService() {
    jooq = new JooqModuleFactory();
  }

  public Seed4JModule buildPostgreSQL(Seed4JModuleProperties properties) {
    return jooq.buildPostgreSQL(properties);
  }

  public Seed4JModule buildMariaDB(Seed4JModuleProperties properties) {
    return jooq.buildMariaDB(properties);
  }

  public Seed4JModule buildMsSQL(Seed4JModuleProperties properties) {
    return jooq.buildMsSQL(properties);
  }

  public Seed4JModule buildMySQL(Seed4JModuleProperties properties) {
    return jooq.buildMySQL(properties);
  }
}
