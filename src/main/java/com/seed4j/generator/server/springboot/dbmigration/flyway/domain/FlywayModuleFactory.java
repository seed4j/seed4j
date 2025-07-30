package com.seed4j.generator.server.springboot.dbmigration.flyway.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FlywayModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/flyway/resources");

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final GroupId FLYWAY_GROUP_ID = groupId("org.flywaydb");

  private static final String PROPERTIES = "properties";

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(FLYWAY_GROUP_ID, artifactId("flyway-core"))
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

  public JHipsterModule buildMysqlDependencyModule(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties).javaDependencies().addDependency(FLYWAY_GROUP_ID, artifactId("flyway-mysql")).and().build();
  }

  public JHipsterModule buildPostgreSQLDependencyModule(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(FLYWAY_GROUP_ID, artifactId("flyway-database-postgresql"))
      .and()
      .build();
  }

  public JHipsterModule buildMsSqlServerDependencyModule(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    return moduleBuilder(properties).javaDependencies().addDependency(FLYWAY_GROUP_ID, artifactId("flyway-sqlserver")).and().build();
  }
}
