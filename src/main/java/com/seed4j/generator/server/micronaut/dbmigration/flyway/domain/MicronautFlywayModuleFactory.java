package com.seed4j.generator.server.micronaut.dbmigration.flyway.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautFlywayModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/dbmigration/flyway");
  private static final Seed4JDestination FLYWAY_CHANGELOG_DESTINATION = to("src/main/resources/db/migration");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .files()
      .add(SOURCE.file("gitkeep"), FLYWAY_CHANGELOG_DESTINATION.append(".gitkeep"))
      .and()
      .javaDependencies()
      .addDependency(groupId("io.micronaut.flyway"), artifactId("micronaut-flyway"))
      .and()
      .springMainProperties()
      .set(propertyKey("flyway.datasources.default.enabled"), propertyValue(true))
      .and()
      .build();
  }
}
