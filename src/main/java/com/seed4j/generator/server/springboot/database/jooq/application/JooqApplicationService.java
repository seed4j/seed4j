package com.seed4j.generator.server.springboot.database.jooq.application;

import com.seed4j.generator.server.springboot.database.jooq.domain.JooqModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JooqApplicationService {

  private final JooqModuleFactory jooq;

  public JooqApplicationService() {
    jooq = new JooqModuleFactory();
  }

  public SeedModule buildPostgreSQL(SeedModuleProperties properties) {
    return jooq.buildPostgreSQL(properties);
  }

  public SeedModule buildMariaDB(SeedModuleProperties properties) {
    return jooq.buildMariaDB(properties);
  }

  public SeedModule buildMsSQL(SeedModuleProperties properties) {
    return jooq.buildMsSQL(properties);
  }

  public SeedModule buildMySQL(SeedModuleProperties properties) {
    return jooq.buildMySQL(properties);
  }
}
