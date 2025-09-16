package com.seed4j.generator.server.springboot.dbmigration.neo4j.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Neo4jMigrationModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/neo4j-migrations");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
