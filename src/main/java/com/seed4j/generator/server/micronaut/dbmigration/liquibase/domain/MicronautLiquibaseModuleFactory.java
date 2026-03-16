package com.seed4j.generator.server.micronaut.dbmigration.liquibase.domain;

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

public class MicronautLiquibaseModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/dbmigration/liquibase");
  private static final Seed4JDestination LIQUIBASE_CHANGELOG_DESTINATION = to("src/main/resources/db/changelog");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .files()
      .add(SOURCE.template("master.xml"), LIQUIBASE_CHANGELOG_DESTINATION.append("master.xml"))
      .and()
      .javaDependencies()
      .addDependency(groupId("io.micronaut.liquibase"), artifactId("micronaut-liquibase"))
      .and()
      .springMainProperties()
      .set(propertyKey("liquibase.datasources.default.change-log"), propertyValue("classpath:db/changelog/master.xml"))
      .and()
      .build();
  }
}
