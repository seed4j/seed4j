package com.seed4j.generator.server.springboot.mvc.sample.flyway.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SampleFlywayModuleFactory {

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/sample/flyway");
  private static final Seed4JDestination MIGRATION_DESTINATION = to("src/main/resources/db/migration/");

  private static final String NOT_POSTGRESQL_CHANGELOG = "00000000000_sample_feature_schema.sql";
  private static final String POSTGRESQL_CHANGELOG = "00000000000_postgresql_sample_feature_schema.sql";

  public Seed4JModule buildPostgreSQLModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file(POSTGRESQL_CHANGELOG), changelogDestination(date))
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildNotPostgreSQLModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file(NOT_POSTGRESQL_CHANGELOG), changelogDestination(date))
        .and()
      .build();
    // @formatter:on
  }

  private Seed4JDestination changelogDestination(Instant date) {
    return MIGRATION_DESTINATION.append(sampleFlywayFilename(date));
  }

  private String sampleFlywayFilename(Instant date) {
    return "V%s__sample_feature_schema.sql".formatted(FILE_DATE_FORMAT.format(date.plusSeconds(1)));
  }
}
