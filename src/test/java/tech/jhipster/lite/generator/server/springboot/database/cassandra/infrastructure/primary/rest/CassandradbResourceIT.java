package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.*;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertLoggerInConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
public class CassandradbResourceIT {

  public final String DATABASE_PATH = "src/main/docker/";

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/cassandra")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    //Check if files exists
    assertFileExist(project, DATABASE_PATH + "cassandra.yml");
    assertFileExist(project, DATABASE_PATH + "app.yml");
    assertFileExist(project, DATABASE_PATH + "cassandra-migration.yml");
    assertFileExist(project, DATABASE_PATH + "cassandra/Cassandra-Migration.Dockerfile");
    assertFileExist(project, DATABASE_PATH + "cassandra/scripts/autoMigrate.sh");
    assertFileExist(project, DATABASE_PATH + "cassandra/scripts/execute-cql.sh");

    //Check if files have content
    //assertFileContent(project,DATABASE_PATH+"cassandra.yml",List.of("image: cassandra:3.11.12","dockerfile: cassandra/Cassandra-Migration.Dockerfile"));
    assertFileContent(project, DATABASE_PATH + "app.yml", "- CREATE_KEYSPACE_SCRIPT=create-keyspace-prod.cql");
    assertFileContent(project, DATABASE_PATH + "cassandra-migration.yml", "- USER=docker-cassandra-migration");
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/Cassandra-Migration.Dockerfile",
      "ADD cassandra/scripts/autoMigrate.sh /usr/local/bin/autoMigrate"
    );
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/scripts/autoMigrate.sh",
      "log \"Use $CREATE_KEYSPACE_SCRIPT_FOLDER/$CREATE_KEYSPACE_SCRIPT script to create the keyspace if necessary\""
    );
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/scripts/execute-cql.sh",
      "cqlsh -k $KEYSPACE_NAME -e \"$query\" $CASSANDRA_CONTACT_POINT"
    );
  }
}
