package com.seed4j.generator.server.springboot.dbmigration.neo4j.application;

import com.seed4j.generator.server.springboot.dbmigration.neo4j.domain.Neo4jMigrationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class Neo4jMigrationApplicationService {

  private final Neo4jMigrationModuleFactory neo4jMigrations;

  public Neo4jMigrationApplicationService() {
    neo4jMigrations = new Neo4jMigrationModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return neo4jMigrations.buildModule(properties);
  }
}
