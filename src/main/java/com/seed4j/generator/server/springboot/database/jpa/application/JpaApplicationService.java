package com.seed4j.generator.server.springboot.database.jpa.application;

import com.seed4j.generator.server.springboot.database.jpa.domain.JpaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaApplicationService {

  private final JpaModuleFactory jpa;

  public JpaApplicationService() {
    jpa = new JpaModuleFactory();
  }

  public JHipsterModule buildPostgreSQL(JHipsterModuleProperties properties) {
    return jpa.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(JHipsterModuleProperties properties) {
    return jpa.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(JHipsterModuleProperties properties) {
    return jpa.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(JHipsterModuleProperties properties) {
    return jpa.buildMySQL(properties);
  }
}
