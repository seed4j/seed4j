package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileContentManyTimes;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.assertFileNoContent;
import static tech.jhipster.lite.TestUtils.assertFileNotExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectBuilder;
import static tech.jhipster.lite.TestUtils.tmpProjectWithLiquibaseMasterXml;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseChangelogMasterXml;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseJava;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseSqlUser;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertLoggerInConfig;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.initClock;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.Liquibase.NEEDLE_LIQUIBASE;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import java.time.Clock;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.domain.MariaDBService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQLService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.PostgresqlService;
import tech.jhipster.lite.generator.tools.domain.BuildToolType;
import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class LiquibaseApplicationServiceIT {

  @Autowired
  BuildToolService buildToolService;

  @Autowired
  SpringBootService springBootService;

  @Autowired
  PostgresqlService postgresqlService;

  @Autowired
  MySQLService mySQLService;

  @Autowired
  MariaDBService mariaDBService;

  @Autowired
  LiquibaseApplicationService liquibaseApplicationService;

  @SpyBean
  Clock clock;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldInit() {
    Project project = tmpProjectBuilder().build();
    buildToolService.init(project, BuildToolType.MAVEN);
    springBootService.init(project);
    postgresqlService.init(project);

    liquibaseApplicationService.init(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.liquibase</groupId>", "<artifactId>liquibase-core</artifactId>", "</dependency>")
    );
    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>com.h2database</groupId>", "<artifactId>h2</artifactId>", "<scope>test</scope>", "</dependency>")
    );
    assertFilesLiquibaseChangelogMasterXml(project);
    assertFilesLiquibaseJava(project);
    assertLoggerInConfig(project);
  }

  @Test
  void shouldAddLiquibase() {
    Project project = tmpProjectBuilder().build();
    buildToolService.init(project, BuildToolType.MAVEN);

    liquibaseApplicationService.addLiquibase(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.liquibase</groupId>", "<artifactId>liquibase-core</artifactId>", "</dependency>")
    );
  }

  @Test
  void shouldAddChangelogMasterXml() {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);

    assertFilesLiquibaseChangelogMasterXml(project);
  }

  @Test
  void shouldAddChangelog() {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");

    String expected = "  <include file=\"config/liquibase/changelog/v1/master.xml\" relativeToChangelogFile=\"false\"/>";

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), List.of(expected, NEEDLE_LIQUIBASE));
  }

  @Test
  void shouldAddChangelogOnlyOneTime() throws Exception {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");

    String expected = "  <include file=\"config/liquibase/changelog/v1/master.xml\" relativeToChangelogFile=\"false\"/>";

    assertFileContentManyTimes(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), expected, 1);
  }

  @Test
  void shouldNotAddChangelogWhenNoMasterXml() throws Exception {
    Project project = tmpProject();

    assertThatThrownBy(() -> liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddConfigurationJava() {
    Project project = tmpProject();

    liquibaseApplicationService.addConfigurationJava(project);

    assertFilesLiquibaseJava(project);
  }

  @Test
  void shouldAddConfigurationJavaWithPackageName() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.lite");

    liquibaseApplicationService.addConfigurationJava(project);

    assertFilesLiquibaseJava(project);
  }

  @Test
  void shouldAddLoggingConfiguration() {
    Project project = tmpProject();
    buildToolService.init(project, BuildToolType.MAVEN);
    springBootService.init(project);
    postgresqlService.init(project);

    liquibaseApplicationService.addLoggerInConfiguration(project);

    assertLoggerInConfig(project);
  }

  @Test
  @DisplayName("should add user and authority changelog for PostgreSQL")
  void shouldAddUserAuthorityChangelogForMyPostgreSQL() {
    Project project = tmpProjectWithLiquibaseMasterXml();

    liquibaseApplicationService.addUserAuthorityChangelog(project);

    assertFilesLiquibaseSqlUser(project);
    assertFileNoContent(
      project,
      getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_entity_User.xml"),
      "autoIncrement=\"true\""
    );
    assertFileExist(project, getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_sequence_User.xml"));
    assertFileContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), "20220128173026_added_sequence_User.xml");
  }

  @ParameterizedTest
  @EnumSource(value = DatabaseType.class, names = { "MYSQL", "MARIADB" })
  @DisplayName("should add user and authority changelog for MySQL or MariaDB")
  void shouldAddUserAuthorityChangelogForMySQLorMariaDB(DatabaseType databaseType) {
    Project project = tmpProject();
    buildToolService.init(project, BuildToolType.MAVEN);
    springBootService.init(project);
    if (databaseType.equals(DatabaseType.MYSQL)) {
      mySQLService.init(project);
    } else {
      mariaDBService.init(project);
    }
    liquibaseApplicationService.init(project);

    liquibaseApplicationService.addUserAuthorityChangelog(project);

    assertFilesLiquibaseSqlUser(project);
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_entity_User.xml"),
      "autoIncrement=\"true\""
    );
    assertFileNotExist(project, getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_sequence_User.xml"));
    assertFileNoContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), "20220128173026_added_sequence_User.xml");
  }
}
