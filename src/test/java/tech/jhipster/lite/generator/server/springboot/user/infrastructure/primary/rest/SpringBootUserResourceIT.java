package tech.jhipster.lite.generator.server.springboot.user.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.MARIADB;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.MONGODB;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.MYSQL;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.POSTGRESQL;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaAuditEntity;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUser;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUserAuthority;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.checkSequence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootUserResourceIT {

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MySQLApplicationService mySQLApplicationService;

  @Autowired
  MariaDBApplicationService mariaDbApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("should add user and authority entities for PostgreSQL")
  void shouldAddUserAndAuthorityEntitiesForPostgreSQL() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/postgresql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, POSTGRESQL);
    assertFilesSqlJavaUserAuthority(project, POSTGRESQL);
    assertFilesSqlJavaAuditEntity(project, POSTGRESQL);

    checkSequence(project, POSTGRESQL);
  }

  @Test
  @DisplayName("should add user and authority entities for MySQL")
  void shouldAddUserAndAuthorityEntitiesForMySQL() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    mySQLApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/mysql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, MYSQL);
    assertFilesSqlJavaUserAuthority(project, MYSQL);
    assertFilesSqlJavaAuditEntity(project, MYSQL);

    checkSequence(project, MYSQL);
  }

  @Test
  @DisplayName("should add user and authority entities for MariaDB")
  void shouldAddUserAndAuthorityEntitiesForMariaDB() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    mariaDbApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/mariadb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, MARIADB);
    assertFilesSqlJavaUserAuthority(project, MARIADB);
    assertFilesSqlJavaAuditEntity(project, MARIADB);

    checkSequence(project, MARIADB);
  }

  @Test
  @DisplayName("should add user and authority entities for MongoDB")
  void shouldAddUserAndAuthorityEntitiesForMongoDB() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    mariaDbApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/mongodb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, MONGODB);
    assertFilesSqlJavaUserAuthority(project, MONGODB);
    assertFilesSqlJavaAuditEntity(project, MONGODB);
  }
}
