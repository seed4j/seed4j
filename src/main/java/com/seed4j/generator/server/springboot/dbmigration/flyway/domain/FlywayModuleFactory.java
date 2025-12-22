package com.seed4j.generator.server.springboot.dbmigration.flyway.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FlywayModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/dbmigration/flyway/resources");

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final GroupId FLYWAY_GROUP_ID = groupId("org.flywaydb");

  private static final String PROPERTIES = "properties";

  public Seed4JModule buildInitializationModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-flyway"))
        .and()
      .files()
        .add(SOURCE.file("V00000000000000__init.sql"), to("src/main/resources/db/migration").append(initFilename(date)))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.flyway.enabled"), propertyValue(true))
        .set(propertyKey("spring.flyway.locations"), propertyValue("classpath:db/migration"))
        .and()
      .build();
    // @formatter:on
  }

  private String initFilename(Instant date) {
    return "V%s__init.sql".formatted(FILE_DATE_FORMAT.format(date));
  }

  public Seed4JModule buildMysqlDependencyModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties).javaDependencies().addDependency(FLYWAY_GROUP_ID, artifactId("flyway-mysql")).and().build();
  }

  public Seed4JModule buildPostgreSQLDependencyModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(FLYWAY_GROUP_ID, artifactId("flyway-database-postgresql"))
      .and()
      .build();
  }

  public Seed4JModule buildMsSqlServerDependencyModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties).javaDependencies().addDependency(FLYWAY_GROUP_ID, artifactId("flyway-sqlserver")).and().build();
  }
}
