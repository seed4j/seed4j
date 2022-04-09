package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.assertInitSqlFile;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.assertProperties;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.assertUserAuthoritySqlFile;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.initClock;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import java.time.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class FlywayApplicationServiceIT {

  @Autowired
  FlywayApplicationService flywayApplicationService;

  @SpyBean
  Clock clock;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    // When
    flywayApplicationService.init(project);

    // Then
    assertDependencies(project);
    assertInitSqlFile(project);
    assertProperties(project);
  }

  @Test
  void shouldAddUserAuthorityChangelog() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    flywayApplicationService.init(project);

    // When
    flywayApplicationService.addUserAuthorityChangelog(project);

    // Then
    assertUserAuthoritySqlFile(project);
  }
}
