package com.seed4j.generator.server.springboot.database.jpa.application;

import com.seed4j.generator.server.springboot.database.jpa.domain.JpaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaApplicationService {

  private final JpaModuleFactory jpa;

  public JpaApplicationService() {
    jpa = new JpaModuleFactory();
  }

  public Seed4JModule buildPostgreSQL(Seed4JModuleProperties properties) {
    return jpa.buildPostgreSQL(properties);
  }

  public Seed4JModule buildMariaDB(Seed4JModuleProperties properties) {
    return jpa.buildMariaDB(properties);
  }

  public Seed4JModule buildMsSQL(Seed4JModuleProperties properties) {
    return jpa.buildMsSQL(properties);
  }

  public Seed4JModule buildMySQL(Seed4JModuleProperties properties) {
    return jpa.buildMySQL(properties);
  }
}
