package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.domain.Project;

public class FlywayAssert {

  static final String CURRENT_DATE = "2022-01-22T16:25:11.0Z";
  public static final ZoneId DEFAULT_TIMEZONE = ZoneId.of("Europe/Paris");

  public static void assertDependencies(Project project) {
    TestUtils.assertFileContent(project, POM_XML, "<flyway.version>");
    TestUtils.assertFileContent(project, POM_XML, "</flyway.version>");
    TestUtils.assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.flywaydb</groupId>",
        "<artifactId>flyway-core</artifactId>",
        "<version>${flyway.version}</version>",
        "</dependency>"
      )
    );
  }

  public static void assertInitSqlFile(Project project) {
    String initSqlFilePath = getPath(MAIN_RESOURCES, "db/migration", "V20220122172511__init.sql");
    assertFileExist(project, initSqlFilePath);
  }

  public static void assertUserAuthoritySqlFile(Project project) {
    String initSqlFilePath = getPath(MAIN_RESOURCES, "db/migration", "V20220122172512__create_user_authority_tables.sql");
    assertFileExist(project, initSqlFilePath);
  }

  public static void assertProperties(Project project) {
    TestUtils.assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of("spring.flyway.enabled=true", "spring.flyway.locations=classpath:db/migration")
    );
  }

  public static void initClock(Clock clock) {
    when(clock.getZone()).thenReturn(DEFAULT_TIMEZONE);
    when(clock.instant()).thenReturn(Instant.parse(CURRENT_DATE));
  }
}
