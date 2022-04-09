package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseChangelogMasterXml;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseJava;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseSqlUser;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseSqlUserAuthority;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.initClock;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;

import java.time.Clock;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.application.PostgresqlApplicationService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class LiquibaseResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  PostgresqlApplicationService postgresqlApplicationService;

  @Autowired
  LiquibaseApplicationService liquibaseApplicationService;

  @Autowired
  MockMvc mockMvc;

  @SpyBean
  Clock clock;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    postgresqlApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/database-migration-tools/liquibase")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

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
  }

  @Test
  void shouldAddUserPostgresql() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    postgresqlApplicationService.init(project);
    liquibaseApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/database-migration-tools/liquibase/user")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesLiquibaseSqlUser(project);
    assertFilesLiquibaseSqlUserAuthority(project);
  }
}
