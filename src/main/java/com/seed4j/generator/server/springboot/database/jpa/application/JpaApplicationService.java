package com.seed4j.generator.server.springboot.database.jpa.application;

import com.seed4j.generator.server.springboot.database.jpa.domain.JpaModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaApplicationService {

  private final JpaModuleFactory jpa;

  public JpaApplicationService() {
    jpa = new JpaModuleFactory();
  }

  public SeedModule buildPostgreSQL(SeedModuleProperties properties) {
    return jpa.buildPostgreSQL(properties);
  }

  public SeedModule buildMariaDB(SeedModuleProperties properties) {
    return jpa.buildMariaDB(properties);
  }

  public SeedModule buildMsSQL(SeedModuleProperties properties) {
    return jpa.buildMsSQL(properties);
  }

  public SeedModule buildMySQL(SeedModuleProperties properties) {
    return jpa.buildMySQL(properties);
  }
}
