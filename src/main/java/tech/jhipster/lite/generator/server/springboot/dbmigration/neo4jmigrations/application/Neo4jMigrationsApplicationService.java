package tech.jhipster.lite.generator.server.springboot.dbmigration.neo4jmigrations.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.neo4jmigrations.domain.Neo4jMigrationsModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class Neo4jMigrationsApplicationService {

  private final Neo4jMigrationsModuleFactory neo4jMigrations;

  public Neo4jMigrationsApplicationService() {
    neo4jMigrations = new Neo4jMigrationsModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return neo4jMigrations.buildModule(properties);
  }
}
