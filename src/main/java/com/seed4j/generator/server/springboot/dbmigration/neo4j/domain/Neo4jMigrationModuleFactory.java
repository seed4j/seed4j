package com.seed4j.generator.server.springboot.dbmigration.neo4j.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.versionSlug;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Neo4jMigrationModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/database/neo4j-migrations");

  public SeedModule buildModule(SeedModuleProperties properties) {
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
