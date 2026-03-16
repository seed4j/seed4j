package com.seed4j.generator.server.micronaut.database.jpa.application;

import com.seed4j.generator.server.micronaut.database.jpa.domain.MicronautDataJpaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautDataJpaApplicationService {

  private final MicronautDataJpaModuleFactory factory;

  public MicronautDataJpaApplicationService() {
    this.factory = new MicronautDataJpaModuleFactory();
  }

  public Seed4JModule buildPostgreSQLModule(Seed4JModuleProperties properties) {
    return factory.buildPostgreSQL(properties);
  }

  public Seed4JModule buildMySQLModule(Seed4JModuleProperties properties) {
    return factory.buildMySQL(properties);
  }

  public Seed4JModule buildMariaDBModule(Seed4JModuleProperties properties) {
    return factory.buildMariaDB(properties);
  }

  public Seed4JModule buildMsSQLModule(Seed4JModuleProperties properties) {
    return factory.buildMsSQL(properties);
  }
}
