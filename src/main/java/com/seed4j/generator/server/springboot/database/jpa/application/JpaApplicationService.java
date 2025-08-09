package com.seed4j.generator.server.springboot.database.jpa.application;

import com.seed4j.generator.server.springboot.database.jpa.domain.JpaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaApplicationService {

  private final JpaModuleFactory jpa;

  public JpaApplicationService() {
    jpa = new JpaModuleFactory();
  }

  public JHipsterModule buildPostgreSQL(SeedModuleProperties properties) {
    return jpa.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(SeedModuleProperties properties) {
    return jpa.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(SeedModuleProperties properties) {
    return jpa.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(SeedModuleProperties properties) {
    return jpa.buildMySQL(properties);
  }
}
