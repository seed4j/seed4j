package com.seed4j.generator.server.springboot.mvc.sample.liquibase.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SampleLiquibaseModuleFactory {

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/liquibase");
  private static final SeedDestination CHANGELOG_DESTINATION = to("src/main/resources/config/liquibase/changelog");

  private static final TextNeedleBeforeReplacer CHANGELOG_NEEDLE = lineBeforeText("<!-- seed4j-needle-liquibase-add-changelog -->");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Instant date = properties.getInstantOrDefault("date", Instant.now());
    String changelogFilename = changelogFilename(date);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.file("00000000000_sample_feature_schema.xml"), CHANGELOG_DESTINATION.append(changelogFilename))
        .and()
      .mandatoryReplacements()
        .in(path("src/main/resources/config/liquibase/master.xml"))
          .add(CHANGELOG_NEEDLE, changelogLine(changelogFilename))
          .and()
        .and()
      .build();
    // @formatter:on
  }

  private String changelogFilename(Instant date) {
    return FILE_DATE_FORMAT.format(date) + "_sample_feature_schema.xml";
  }

  private String changelogLine(String changelogFilename) {
    return "<include file=\"config/liquibase/changelog/%s\" relativeToChangelogFile=\"false\"/>".formatted(changelogFilename);
  }
}
