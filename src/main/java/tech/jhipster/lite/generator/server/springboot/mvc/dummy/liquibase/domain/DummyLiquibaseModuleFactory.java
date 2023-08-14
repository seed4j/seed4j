package tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class DummyLiquibaseModuleFactory {

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/dummy/liquibase");
  private static final JHipsterDestination CHANGELOG_DESTINATION = to("src/main/resources/config/liquibase/changelog");

  private static final TextNeedleBeforeReplacer CHANGELOG_NEEDLE = lineBeforeText("<!-- jhipster-needle-liquibase-add-changelog -->");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());
    String changelogFilename = changelogFilename(date);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file("00000000000_dummy_feature_schema.xml"), CHANGELOG_DESTINATION.append(changelogFilename))
        .and()
      .mandatoryReplacements()
        .in(path("src/main/resources/config/liquibase/master.xml"))
          .add(CHANGELOG_NEEDLE, changelogLine(changelogFilename))
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String changelogFilename(Instant date) {
    return FILE_DATE_FORMAT.format(date) + "_dummy_feature_schema.xml";
  }

  private String changelogLine(String changelogFilename) {
    return new StringBuilder()
      .append("<include file=\"config/liquibase/changelog/")
      .append(changelogFilename)
      .append("\" relativeToChangelogFile=\"false\"/>")
      .toString();
  }
}
