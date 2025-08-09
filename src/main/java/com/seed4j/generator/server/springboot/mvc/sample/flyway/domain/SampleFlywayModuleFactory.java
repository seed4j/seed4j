package com.seed4j.generator.server.springboot.mvc.sample.flyway.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SampleFlywayModuleFactory {

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/flyway");
  private static final SeedDestination MIGRATION_DESTINATION = to("src/main/resources/db/migration/");

  private static final String NOT_POSTGRESQL_CHANGELOG = "00000000000_sample_feature_schema.sql";
  private static final String POSTGRESQL_CHANGELOG = "00000000000_postgresql_sample_feature_schema.sql";

  public JHipsterModule buildPostgreSQLModule(JHipsterModuleProperties properties) {
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

  public JHipsterModule buildNotPostgreSQLModule(JHipsterModuleProperties properties) {
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

  private SeedDestination changelogDestination(Instant date) {
    return MIGRATION_DESTINATION.append(sampleFlywayFilename(date));
  }

  private String sampleFlywayFilename(Instant date) {
    return "V%s__sample_feature_schema.sql".formatted(FILE_DATE_FORMAT.format(date.plusSeconds(1)));
  }
}
