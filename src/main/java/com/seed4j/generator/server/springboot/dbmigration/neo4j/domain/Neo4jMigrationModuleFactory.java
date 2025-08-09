package com.seed4j.generator.server.springboot.dbmigration.neo4j.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Neo4jMigrationModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/database/neo4j-migrations");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("eu.michael-simons.neo4j"), artifactId("neo4j-migrations-spring-boot-starter"), versionSlug("neo4j-migrations"))
        .and()
      .documentation(documentationTitle("Neo4j Migrations"), SOURCE.append("neo4j-migrations.md"))
      .springMainProperties()
        .set(propertyKey("org.neo4j.migrations.check-location"), propertyValue(false))
        .and()
      .build();
    // @formatter:on
  }
}
