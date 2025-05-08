package tech.jhipster.lite.generator.server.springboot.database.jpa.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.jpa.domain.JpaModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
