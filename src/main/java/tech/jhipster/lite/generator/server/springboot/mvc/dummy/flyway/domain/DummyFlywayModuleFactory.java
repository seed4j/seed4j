package tech.jhipster.lite.generator.server.springboot.mvc.dummy.flyway.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class DummyFlywayModuleFactory {

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/dummy/flyway");
  private static final JHipsterDestination MIGRATION_DESTINATION = to("src/main/resources/db/migration/");

  private static final String NOT_POSTGRESQL_CHANGELOG = "00000000000_dummy_feature_schema.sql";
  private static final String POSTGRESQL_CHANGELOG = "00000000000_postgresql_dummy_feature_schema.sql";

  public JHipsterModule buildPostgresqlModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file(POSTGRESQL_CHANGELOG), changelogDestination(date))
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildNotPostgresqlModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file(NOT_POSTGRESQL_CHANGELOG), changelogDestination(date))
        .and()
      .build();
    //@formatter:on
  }

  private JHipsterDestination changelogDestination(Instant date) {
    return MIGRATION_DESTINATION.append(dummyFlywayFilename(date));
  }

  private String dummyFlywayFilename(Instant date) {
    return new StringBuilder()
      .append("V")
      .append(FILE_DATE_FORMAT.format(date.plusSeconds(1)))
      .append("__dummy_feature_schema.sql")
      .toString();
  }
}
